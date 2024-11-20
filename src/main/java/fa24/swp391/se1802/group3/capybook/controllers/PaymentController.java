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

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam int totalAmount, HttpServletRequest request) {
        try {
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/payment/return";

            // Lấy IP từ request
            String clientIp = getClientIpAddr(request);
            System.out.println("Client IP Address: " + clientIp);

            // Gọi hàm tạo URL thanh toán
            String paymentUrl = vnPayService.createPaymentUrl(totalAmount, returnUrl, request);

            return ResponseEntity.status(HttpStatus.OK).body(paymentUrl);
        } catch (Exception e) {
            System.err.println("Error while creating payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/return")
    public ResponseEntity<String> handlePaymentReturn(HttpServletRequest request) {
        try {
            // Log tất cả các tham số trả về từ VNPay
            request.getParameterMap().forEach((key, value) -> {
                System.out.println("VNPay Response Parameter: " + key + " = " + String.join(", ", value));
            });

            boolean paymentSuccess = vnPayService.verifyPayment(request);

            if (paymentSuccess) {
                return ResponseEntity.status(HttpStatus.OK).body("Payment successful!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("Error while verifying payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Nếu IP là localhost (IPv6), chuyển sang IPv4
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

}
