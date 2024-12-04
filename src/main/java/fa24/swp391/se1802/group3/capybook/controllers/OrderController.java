package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.*;
import fa24.swp391.se1802.group3.capybook.models.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderID", order.getOrderID());
            orderMap.put("orderDate", order.getOrderDate());
            orderMap.put("orderStatus", order.getOrderStatus());
            orderMap.put("username", order.getUsername().getUsername());
            orderMap.put("orderAddress", order.getOrderAddress()); // Thêm orderAddress

            if (order.getProID() != null) {
                orderMap.put("proID", order.getProID().getProID());
                orderMap.put("discount", order.getProID().getDiscount());
            }

            response.put("order", orderMap);

            List<Map<String, Object>> orderDetailResponses = orderDetails.stream()
                    .map(detail -> {
                        Map<String, Object> detailMap = new HashMap<>();
                        detailMap.put("ODID", detail.getOdid());
                        detailMap.put("quantity", detail.getQuantity());
                        detailMap.put("bookID", detail.getBookID().getBookID());
                        detailMap.put("totalPrice", detail.getTotalPrice()); // Thêm totalPrice
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
            Map<String, Object> orderDTOMap = (Map<String, Object>) orderData.get("orderDTO");
            List<Map<String, Object>> orderDetailsList = (List<Map<String, Object>>) orderData.get("orderDetails");

            if (orderDTOMap == null || orderDetailsList == null || orderDetailsList.isEmpty()) {
                return new ResponseEntity<>("Invalid order data: Missing required fields.", HttpStatus.BAD_REQUEST);
            }

            // Tạo đối tượng OrderDTO
            OrderDTO orderDTO = new OrderDTO();
            String username = (String) orderDTOMap.get("username");
            AccountDTO account = accountDAO.findByUsername(username);
            if (account == null) {
                return new ResponseEntity<>("Invalid username: User does not exist.", HttpStatus.BAD_REQUEST);
            }
            orderDTO.setUsername(account);

            // Xử lý khuyến mãi (nếu có)
            Integer proID = (Integer) orderDTOMap.get("proID");
            if (proID != null) {
                PromotionDTO promotion = promotionDAO.find(proID);
                if (promotion == null || promotion.getQuantity() <= 0) {
                    return new ResponseEntity<>("Invalid promotion: Not available or out of stock.", HttpStatus.BAD_REQUEST);
                }
                promotion.setQuantity(promotion.getQuantity() - 1);
                promotionDAO.update(promotion);
                orderDTO.setProID(promotion);
            }

            // Đặt ngày và trạng thái đơn hàng
            orderDTO.setOrderDate(new Date());
            orderDTO.setOrderStatus(0);

            // Lưu OrderDTO
            orderDAO.save(orderDTO);

            // Xử lý chi tiết đơn hàng
            for (Map<String, Object> detail : orderDetailsList) {
                Integer bookID = (Integer) detail.get("bookID");
                Integer quantity = (Integer) detail.get("quantity");
                Object totalPriceObj = detail.get("totalPrice");

                // Kiểm tra null cho các giá trị cần thiết
                if (bookID == null || quantity == null || quantity <= 0 || totalPriceObj == null) {
                    return new ResponseEntity<>("Invalid order details: Missing or invalid fields.", HttpStatus.BAD_REQUEST);
                }

                BigDecimal totalPrice = new BigDecimal(totalPriceObj.toString());

                // Tạo OrderDetailDTO và lưu
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setOrderID(orderDTO);

                // Thiết lập bookID
                BookDTO book = new BookDTO();
                book.setBookID(bookID);
                orderDetailDTO.setBookID(book);

                orderDetailDTO.setQuantity(quantity);
                orderDetailDTO.setTotalPrice(totalPrice);

                orderDetailDAO.save(orderDetailDTO);
            }

            return new ResponseEntity<>("Order added successfully with ID: " + orderDTO.getOrderID(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
