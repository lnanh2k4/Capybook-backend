package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.PromotionDAO;
import fa24.swp391.se1802.group3.capybook.daos.PromotionLogDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.models.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionDAO promotionDAO;
    private final StaffDAO staffDAO;
    private final AccountDAO accountDAO;
    private final PromotionLogDAO promotionLogDAO;

    @Autowired
    public PromotionController(PromotionDAO promotionDAO, StaffDAO staffDAO, AccountDAO accountDAO, PromotionLogDAO promotionLogDAO) {
        this.promotionDAO = promotionDAO;
        this.staffDAO = staffDAO;
        this.accountDAO = accountDAO; // Inject AccountDAO
        this.promotionLogDAO = promotionLogDAO;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPromotions() {
        try {
            // Fetch all promotions từ DAO
            List<PromotionDTO> promotions = promotionDAO.findAll();

            // Map promotions sang response DTO
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

                // Fetch staff ID hoặc null
                response.put("createdBy", promotion.getCreatedBy() != null
                        ? promotion.getCreatedBy().getStaffID()
                        : null);
                response.put("approvedBy", promotion.getApprovedBy() != null
                        ? promotion.getApprovedBy().getStaffID()
                        : null);
                return response;
            }).toList();

            // Return the response
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching promotions.");
        }
    }

    @GetMapping("/{proID}")
    public ResponseEntity<?> getPromotionById(@PathVariable int proID) {
        try {
            PromotionDTO promotion = promotionDAO.find(proID);
            if (promotion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promotion not found");
            }

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

            // Fetch staff ID hoặc null
            response.put("createdBy", promotion.getCreatedBy() != null
                    ? promotion.getCreatedBy().getStaffID()
                    : null);
            response.put("approvedBy", promotion.getApprovedBy() != null
                    ? promotion.getApprovedBy().getStaffID()
                    : null);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addPromotion(
            @RequestBody PromotionDTO promotionDTO,
            @RequestParam(name = "username") String username) {
        try {
            System.out.println("Received promotion data: " + promotionDTO);
            System.out.println("Received username: " + username);

            // Kiểm tra account
            AccountDTO accountDTO = accountDAO.findByUsername(username);
            if (accountDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid username: Account not found.");
            }

            // Kiểm tra staff
            StaffDTO staff = staffDAO.findStaff(username);
            System.out.println("Received staff: " + staff);
            if (staff == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid username: Staff not found.");
            }

            // Kiểm tra dữ liệu Promotion
            if (promotionDTO.getProName() == null || promotionDTO.getProCode() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Promotion name and code are required.");
            }

            // Lưu promotion
            promotionDTO.setCreatedBy(staff);
            promotionDTO.setProStatus(1); // Mặc định là Active
            promotionDAO.save(promotionDTO);

            System.out.println("Promotion saved successfully: " + promotionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(promotionDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while saving promotion.");
        }
    }

    @PutMapping("/{proID}")
    @Transactional
    public ResponseEntity<?> updatePromotion(
            @PathVariable int proID,
            @RequestBody Map<String, Object> updates) {
        try {
            System.out.println("Received request to update promotion with ID: " + proID);
            System.out.println("Updates: " + updates);

            // Kiểm tra tồn tại của promotion
            PromotionDTO existingPromotion = promotionDAO.find(proID);
            if (existingPromotion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Promotion with ID " + proID + " not found.");
            }

            // Ghi log hoạt động nếu có proAction
            if (updates.containsKey("actionId")) {
                Integer actionId = (Integer) updates.get("actionId");
                if (actionId == 2 || actionId == 3) { // Approved hoặc Rejected
                    PromotionLogDTO promotionLog = new PromotionLogDTO();
                    promotionLog.setProId(existingPromotion);
                    promotionLog.setProAction(actionId);
                    promotionLog.setProLogDate(new Date());

                    // Kiểm tra và thiết lập staffUsername
                    if (!updates.containsKey("username")) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Missing username in request.");
                    }

                    String staffUsername = (String) updates.get("username");
                    System.out.println("Looking for staff with username: " + staffUsername);
                    StaffDTO staff = staffDAO.findStaff(staffUsername);

                    if (staff == null) {
                        System.out.println("Staff not found for username: " + staffUsername);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Invalid staffUsername: Staff not found.");
                    }

                    System.out.println("Found staff: " + staff);
                    System.out.println("Staff ID: " + staff.getStaffID());

                    // Nếu actionId là approve
                    if (actionId == 2) {
                        existingPromotion.setApprovedBy(staff);
                    } else if (actionId == 3) {
                        existingPromotion.setProStatus(0); // Đánh dấu là đã từ chối
                    }

                    // Lưu log
                    promotionLog.setStaffId(staff);
                    System.out.println("Saving promotion log: " + promotionLog);
                    promotionLogDAO.save(promotionLog);

                    // Cập nhật promotion
                    promotionDAO.update(existingPromotion);

                    String actionMessage = actionId == 2 ? "approved" : "declined";
                    System.out.println("Promotion " + actionMessage + " successfully: " + existingPromotion);
                    return ResponseEntity.ok("Promotion " + actionMessage + " successfully.");
                } else {
                    System.out.println("Invalid actionId: " + actionId);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid actionId. Must be 2 (approve) or 3 (reject).");
                }
            }

            // Nếu không có actionId, trả về lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Missing or invalid actionId in request.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the promotion.");
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
    @GetMapping("/logs")
    public ResponseEntity<?> getAllPromotionLogs() {
        try {
            List<PromotionLogDTO> logs = promotionLogDAO.findAll();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching promotion logs.");
        }
    }

}
