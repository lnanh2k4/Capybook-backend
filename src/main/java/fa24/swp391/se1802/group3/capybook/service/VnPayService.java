package fa24.swp391.se1802.group3.capybook.service;

import fa24.swp391.se1802.group3.capybook.configs.VnPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VnPayService {

    public String createPaymentUrl(int totalAmount, String returnUrl, HttpServletRequest request) {
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Return URL: " + returnUrl);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8); // Transaction ID
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode; // Terminal Code

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount * 100)); // Chuyển sang đơn vị nhỏ nhất
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Payment for order #" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);

        // Lấy địa chỉ IP của khách hàng
        String clientIp = getClientIpAddr(request);
        vnp_Params.put("vnp_IpAddr", clientIp);

        String vnp_CreateDate = getCurrentDateTime();
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        String vnp_ExpireDate = getExpireDateTime(15); // 15 phút
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Log tất cả các tham số gửi đến VNPay
        System.out.println("VNPay Parameters: " + vnp_Params);

        String queryUrl = buildQueryUrl(vnp_Params);
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, queryUrl);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String finalUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

        // Log URL cuối cùng
        System.out.println("Final Payment URL: " + finalUrl);

        return finalUrl;
    }


    public boolean verifyPayment(HttpServletRequest request) {
        Map<String, String> fields = extractParameters(request);

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        String generatedHash = VnPayConfig.hashAllFields(fields);
        boolean isValid = generatedHash.equals(vnp_SecureHash) && "00".equals(request.getParameter("vnp_TransactionStatus"));

        // Log kết quả xác thực
        System.out.println("Payment Verified: " + isValid);

        return isValid;
    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }

    private String getExpireDateTime(int minutes) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        calendar.add(Calendar.MINUTE, minutes);
        return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }

    private String buildQueryUrl(Map<String, String> params) {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);

        StringBuilder queryBuilder = new StringBuilder();
        for (String key : sortedKeys) {
            try {
                String value = URLEncoder.encode(params.get(key), StandardCharsets.US_ASCII.toString());
                queryBuilder.append(key).append('=').append(value).append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        queryBuilder.setLength(queryBuilder.length() - 1); // Bỏ '&' cuối cùng
        return queryBuilder.toString();
    }

    private Map<String, String> extractParameters(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> fields.put(key, value[0]));
        return fields;
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
        return ip;
    }


}
