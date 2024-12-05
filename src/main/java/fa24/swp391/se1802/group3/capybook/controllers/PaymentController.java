package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "http://localhost:5175")
public class PaymentController {

    private final VnPayService vnPayService;

    @Autowired
    public PaymentController(VnPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    /**
     * Tạo URL thanh toán VNPay
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam long totalAmount, HttpServletRequest request) {
        try {
            // Xác định URL trả về sau khi thanh toán
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/payment/return";

            // Gọi service để tạo URL thanh toán
            String paymentUrl = vnPayService.createOrder(totalAmount, "Thanh toán đơn hàng", returnUrl, request);

            return ResponseEntity.status(HttpStatus.OK).body(paymentUrl); // Trả về URL thanh toán
        } catch (Exception e) {
            System.err.println("Error while creating payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Xử lý trả về từ VNPay sau thanh toán
     */
    @GetMapping("/return")
    public ResponseEntity<String> handlePaymentReturn(HttpServletRequest request) {
        try {
            // Ghi log toàn bộ tham số trả về từ VNPay
            request.getParameterMap().forEach((key, value) -> {
                System.out.println("VNPay Response Parameter: " + key + " = " + String.join(", ", value));
            });

            // Xác minh giao dịch
            int paymentStatus = vnPayService.orderReturn(request);

            // Phản hồi kết quả
            if (paymentStatus == 1) {
                return ResponseEntity.status(HttpStatus.OK).body("Payment successful!");
            } else if (paymentStatus == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Please try again.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature.");
            }
        } catch (Exception e) {
            System.err.println("Error while verifying payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
