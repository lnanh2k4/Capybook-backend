package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.PromotionDAO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionDAO promotionDAO;

    @Autowired
    public PromotionController(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    // Lấy danh sách tất cả các khuyến mãi
    @GetMapping("/")
    public List<PromotionDTO> getAllPromotions() {
        return promotionDAO.findAll();  // Sử dụng JPQL để lấy danh sách khuyến mãi
    }

    // Lấy chi tiết một khuyến mãi dựa trên proID
    @GetMapping("/{proID}")
    public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable int proID) {
        PromotionDTO promotion = promotionDAO.find(proID); // Gọi hàm find từ DAO
        if (promotion != null) {
            return new ResponseEntity<>(promotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Thêm mới một khuyến mãi
    @PostMapping("/")
    public ResponseEntity<PromotionDTO> addPromotion(@RequestBody PromotionDTO promotionDTO) {
        promotionDAO.save(promotionDTO);
        return new ResponseEntity<>(promotionDTO, HttpStatus.CREATED);
    }

    // Cập nhật thông tin khuyến mãi
    @PutMapping("/{proID}")
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable int proID, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO existingPromotion = promotionDAO.find(proID);
        if (existingPromotion != null) {
            promotionDAO.update(promotionDTO);
            return new ResponseEntity<>(promotionDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/soft-delete/{proID}")
    public ResponseEntity<String> softDeletePromotion(@PathVariable int proID) {
        try {
            PromotionDTO promotion = promotionDAO.find(proID); // Tìm khuyến mãi theo ID
            if (promotion == null) {
                return new ResponseEntity<>("Promotion not found", HttpStatus.NOT_FOUND);
            }
            promotion.setProStatus(0); // Đặt proStatus thành 0 để đánh dấu đã xóa
            promotionDAO.update(promotion); // Cập nhật khuyến mãi với proStatus = 0
            return new ResponseEntity<>("Promotion marked as deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error marking promotion as deleted: " + e.getMessage());
            return new ResponseEntity<>("Failed to mark promotion as deleted due to an internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
