package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.OrderDAO;
import fa24.swp391.se1802.group3.capybook.daos.OrderDetailDAO;
import fa24.swp391.se1802.group3.capybook.models.OrderDTO;
import fa24.swp391.se1802.group3.capybook.models.OrderDetailDTO;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("/")
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = orderDAO.findAll();
        for (OrderDTO order : orders) {
            if (order.getUsername() != null) {
                order.setCustomerName(order.getUsername().getUsername()); // Sử dụng username trực tiếp
            }
        }
        return orders;
    }

    @GetMapping("/{orderID}")
    @Transactional
    public ResponseEntity<Map<String, Object>> getOrderWithDetails(@PathVariable int orderID) {
        OrderDTO order = orderDAO.find(orderID);
        List<OrderDetailDTO> orderDetails = orderDetailDAO.findByOrderID(orderID);

        if (order != null) {
            Map<String, Object> response = new HashMap<>();

            // Thêm thông tin của đơn hàng vào phản hồi, bao gồm thông tin chiết khấu nếu có
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderID", order.getOrderID());
            orderMap.put("orderDate", order.getOrderDate());
            orderMap.put("orderStatus", order.getOrderStatus());
            orderMap.put("username", order.getUsername().getUsername()); // Đưa username vào trong order

            // Kiểm tra và thêm thông tin khuyến mãi nếu có
            if (order.getProID() != null) {
                orderMap.put("proID", order.getProID().getProID());
                orderMap.put("discount", order.getProID().getDiscount());
            }

            response.put("order", orderMap);

            // Tạo danh sách orderDetails
            List<Map<String, Object>> orderDetailResponses = orderDetails.stream()
                    .map(detail -> {
                        Map<String, Object> detailMap = new HashMap<>();
                        detailMap.put("ODID", detail.getOdid());
                        detailMap.put("quantity", detail.getQuantity());
                        detailMap.put("bookID", detail.getBookID().getBookID()); // Chỉ lấy bookID
                        return detailMap;
                    })
                    .collect(Collectors.toList());

            response.put("orderDetails", orderDetailResponses);

            return new ResponseEntity<>(response, HttpStatus.OK);
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

    @PutMapping("/{orderID}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int orderID, @RequestBody Map<String, Integer> updateData) {
        OrderDTO existingOrder = orderDAO.find(orderID);
        if (existingOrder != null) {
            if (updateData.containsKey("orderStatus")) {
                existingOrder.setOrderStatus(updateData.get("orderStatus"));
                orderDAO.update(existingOrder);
                return new ResponseEntity<>(existingOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
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


    @GetMapping("/search")
    public ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam("term") int orderID) {
        List<OrderDTO> orders = orderDAO.searchByOrderId(orderID);
        for (OrderDTO order : orders) {
            if (order.getUsername() != null) {
                order.setCustomerName(order.getUsername().getFirstName() + " " + order.getUsername().getLastName());
            }
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
