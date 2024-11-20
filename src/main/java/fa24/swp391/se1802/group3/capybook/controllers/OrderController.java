package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.*;
import fa24.swp391.se1802.group3.capybook.models.*;
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
    private final BookDAO bookDAO;
    private final PromotionDAO promotionDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO, OrderDetailDAO orderDetailDAO, BookDAO bookDAO, PromotionDAO promotionDAO, AccountDAO accountDAO) {
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
        this.bookDAO = bookDAO;
        this.promotionDAO = promotionDAO;
        this.accountDAO = accountDAO;
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

    @PostMapping("/")
    @Transactional
    public ResponseEntity<String> addOrder(@RequestBody Map<String, Object> orderData) {
        try {
            // Lấy thông tin orderDTO từ request
            Map<String, Object> orderDTOMap = (Map<String, Object>) orderData.get("orderDTO");
            List<Map<String, Object>> orderDetailsList = (List<Map<String, Object>>) orderData.get("orderDetails");

            // Tạo đối tượng OrderDTO
            OrderDTO orderDTO = new OrderDTO();
            AccountDTO account = accountDAO.findByUsername((String) orderDTOMap.get("username"));
            orderDTO.setUsername(account);

            // Xử lý khuyến mãi (nếu có)
            if (orderDTOMap.get("proID") != null) {
                PromotionDTO promotion = promotionDAO.find((Integer) orderDTOMap.get("proID"));
                if (promotion.getQuantity() > 0) {
                    promotion.setQuantity(promotion.getQuantity() - 1); // Giảm số lượng khuyến mãi
                    promotionDAO.update(promotion);
                    orderDTO.setProID(promotion);
                } else {
                    return new ResponseEntity<>("Promotion is out of stock", HttpStatus.BAD_REQUEST);
                }
            }

            // Đặt các giá trị mặc định cho đơn hàng
            orderDTO.setOrderDate(new Date());
            orderDTO.setOrderStatus(1);

            // Lưu OrderDTO
            orderDAO.save(orderDTO);

            // Lấy orderID vừa tạo
            int orderID = orderDTO.getOrderID();

            // Lưu các OrderDetailDTO và giảm số lượng sách
            for (Map<String, Object> detail : orderDetailsList) {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setOrderID(orderDTO);

                BookDTO book = bookDAO.find((Integer) detail.get("bookID"));
                int quantityToDeduct = (Integer) detail.get("quantity");

                if (book.getBookQuantity() >= quantityToDeduct) {
                    book.setBookQuantity(book.getBookQuantity() - quantityToDeduct); // Giảm số lượng sách
                    bookDAO.update(book); // Cập nhật sách trong DB
                    orderDetailDTO.setBookID(book);
                    orderDetailDTO.setQuantity(quantityToDeduct);
                    orderDetailDAO.save(orderDetailDTO);
                } else {
                    return new ResponseEntity<>("Book stock is insufficient for book ID: " + book.getBookID(),
                            HttpStatus.BAD_REQUEST);
                }
            }

            return new ResponseEntity<>("Order added successfully with ID: " + orderID, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
