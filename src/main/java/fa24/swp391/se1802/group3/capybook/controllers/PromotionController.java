package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.PromotionDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionDAO promotionDAO;
    private final StaffDAO staffDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public PromotionController(PromotionDAO promotionDAO, StaffDAO staffDAO, AccountDAO accountDAO) {
        this.promotionDAO = promotionDAO;
        this.staffDAO = staffDAO;
        this.accountDAO = accountDAO; // Inject AccountDAO
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPromotions() {
        try {
            // Fetch all promotions from DAO
            List<PromotionDTO> promotions = promotionDAO.findAll();

            // Map the promotions to a response format
            List<Map<String, Object>> responseList = promotions.stream().map(promotion -> {
                Map<String, Object> response = new HashMap<>();
                response.put("proID", promotion.getProID());
                response.put("proName", promotion.getProName());
                response.put("proCode", promotion.getProCode());
                response.put("discount", promotion.getDiscount());
                response.put("startDate", promotion.getStartDate());
                response.put("endDate", promotion.getEndDate());
                response.put("quantity", promotion.getQuantity());
                response.put("proStatus", promotion.getProStatus());
                response.put("createdBy", promotion.getCreatedBy() != null ? promotion.getCreatedBy().getStaffID() : null);
                response.put("approvedBy", promotion.getApprovedBy() != null ? promotion.getApprovedBy().getStaffID() : null);
                return response;
            }).toList();

            // Return the response
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching promotions.");
        }
    }


    @GetMapping("/{proID}")
    public ResponseEntity<?> getPromotionById(@PathVariable int proID) {
        try {
            PromotionDTO promotion = promotionDAO.find(proID); // Lấy thông tin khuyến mãi từ DAO
            if (promotion != null) {
                // Tạo phản hồi JSON chỉ chứa các trường cần thiết
                Map<String, Object> response = new HashMap<>();
                response.put("proID", promotion.getProID());
                response.put("proName", promotion.getProName());
                response.put("proCode", promotion.getProCode());
                response.put("discount", promotion.getDiscount());
                response.put("startDate", promotion.getStartDate());
                response.put("endDate", promotion.getEndDate());
                response.put("quantity", promotion.getQuantity());
                response.put("proStatus", promotion.getProStatus());

                // Chỉ lấy staffID cho createdBy và approvedBy
                response.put("createdBy", promotion.getCreatedBy() != null ? promotion.getCreatedBy().getStaffID() : null);
                response.put("approvedBy", promotion.getApprovedBy() != null ? promotion.getApprovedBy().getStaffID() : null);

                return ResponseEntity.ok(response); // Trả về phản hồi JSON
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promotion not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


    @PostMapping("/")
    public ResponseEntity<PromotionDTO> addPromotion(
            @RequestBody PromotionDTO promotionDTO,
            @RequestParam(name = "username") String username) { // @RequestParam để nhận username
        try {
            System.out.println("Request received: " + promotionDTO);

            // Tra cứu account dựa trên username
            AccountDTO accountDTO = accountDAO.findByUsername(username);
            if (accountDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Tra cứu staff dựa trên username
            StaffDTO staff = staffDAO.findStaff(username);
            if (staff == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Gắn staff vào createdBy
            promotionDTO.setCreatedBy(staff);

            // Lưu promotion vào cơ sở dữ liệu
            promotionDTO.setProStatus(1); // Đặt trạng thái mặc định
            promotionDAO.save(promotionDTO);

            System.out.println("Promotion saved in database: " + promotionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(promotionDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{proID}")
    public ResponseEntity<PromotionDTO> updatePromotion(
            @PathVariable int proID,
            @RequestBody Map<String, Object> updates) {
        PromotionDTO existingPromotion = promotionDAO.find(proID);
        if (existingPromotion != null) {
            // Kiểm tra và giữ nguyên `createdBy` nếu không thay đổi
            if (!updates.containsKey("proName")) {
                updates.put("proName", existingPromotion.getProName());
            }
            // Các trường khác tương tự...

            // Xử lý `approvedByUsername`
            if (updates.containsKey("approvedByUsername")) {
                String approvedByUsername = (String) updates.get("approvedByUsername");
                AccountDTO accountDTO = accountDAO.findByUsername(approvedByUsername);
                if (accountDTO == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                StaffDTO staff = staffDAO.findStaff(approvedByUsername);
                if (staff == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                existingPromotion.setApprovedBy(staff);
            }

            // Cập nhật các trường khác nếu cần thiết

            promotionDAO.update(existingPromotion);
            return new ResponseEntity<>(existingPromotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{proID}")
    public ResponseEntity<String> deletePromotion(@PathVariable int proID) {
        PromotionDTO existingPromotion = promotionDAO.find(proID);
        if (existingPromotion != null) {
            // Thay vì xóa, chúng ta sẽ cập nhật proStatus thành 0
            existingPromotion.setProStatus(0);  // Đánh dấu đã xóa
            promotionDAO.update(existingPromotion);  // Cập nhật thông tin khuyến mãi
            return ResponseEntity.ok("Promotion marked as deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promotion not found");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PromotionDTO>> searchPromotions(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String term) {
        if (id != null) {
            PromotionDTO promotion = promotionDAO.find(id);
            if (promotion != null) {
                return ResponseEntity.ok(List.of(promotion));
            } else {
                return ResponseEntity.ok(List.of());
            }
        }

        if (term != null && !term.isEmpty()) {
            List<PromotionDTO> promotions = promotionDAO.searchPromotions(term);
            return ResponseEntity.ok(promotions);
        }

        return ResponseEntity.ok(List.of());
    }

}
