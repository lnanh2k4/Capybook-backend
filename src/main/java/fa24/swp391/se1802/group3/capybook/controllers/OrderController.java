package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.OrderDAO;
import fa24.swp391.se1802.group3.capybook.daos.OrderDetailDAO;
import fa24.swp391.se1802.group3.capybook.models.OrderDTO;
import fa24.swp391.se1802.group3.capybook.models.OrderDetailDTO;
import fa24.swp391.se1802.group3.capybook.models.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderDAO orderDAO;

    private final OrderDetailDAO orderDetailDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO, OrderDetailDAO orderDetailDAO) {
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
    }
    // Lấy danh sách tất cả các đơn hàng
    @GetMapping("/")
    public List<OrderDTO> getAllOrders() {
        return orderDAO.findAll();  // Sử dụng JPQL để lấy danh sách đơn hàng
    }

    // Lấy chi tiết một đơn hàng dựa trên orderID
    @GetMapping("/{orderID}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int orderID) {
        OrderDTO order = orderDAO.find(orderID);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Thêm mới một đơn hàng
    @PostMapping("/")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        orderDAO.save(orderDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    // Cập nhật thông tin đơn hàng
    @PutMapping("/{orderID}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int orderID, @RequestBody OrderDTO orderDTO) {
        OrderDTO existingOrder = orderDAO.find(orderID);
        if (existingOrder != null) {
            orderDAO.update(orderDTO);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Xóa đơn hàng
    @DeleteMapping("/{orderID}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderID) {
        OrderDTO existingOrder = orderDAO.find(orderID);
        if (existingOrder != null) {
            orderDAO.delete(orderID);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createOrderWithDetails")
    public ResponseEntity<OrderDTO> createOrderWithDetails(@RequestBody OrderRequest orderRequest) {

        // Lưu thông tin đơn hàng
        OrderDTO orderDTO = orderRequest.getOrderDTO();
        orderDAO.save(orderDTO);  // Lưu đơn hàng

        // Lưu các chi tiết đơn hàng (OrderDetail)
        for (OrderDetailDTO detail : orderRequest.getOrderDetails()) {
            detail.setOrderID(orderDTO);  // Gán đối tượng OrderDTO thay vì chỉ gán orderID
            orderDetailDAO.save(detail);  // Lưu từng chi tiết đơn hàng
        }

        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

//    @GetMapping("/details/{id}")
//    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrderID(@PathVariable int id) {
//        List<OrderDetailDTO> orderDetails = orderDetailDAO.findByOrderID(id);
//        if (!orderDetails.isEmpty()) {
//            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
