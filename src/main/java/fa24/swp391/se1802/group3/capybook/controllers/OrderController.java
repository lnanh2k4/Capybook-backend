package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.OrderDAO;
import fa24.swp391.se1802.group3.capybook.models.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
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
}
