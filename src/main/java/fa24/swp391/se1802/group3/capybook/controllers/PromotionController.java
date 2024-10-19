package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.PromotionDAO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping("/")
    public ResponseEntity<PromotionDTO> addPromotion(@RequestBody String promotionData) {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            PromotionDTO promotion = objectMapper.readValue(promotionData, PromotionDTO.class);
            System.out.println("Parsed promotion data: " + promotion);

            // Đặt trạng thái khuyến mãi nếu cần
            promotion.setProStatus(1);

            // Lưu promotion vào cơ sở dữ liệu, proID sẽ tự động được gán
            promotionDAO.save(promotion);
            System.out.println("Promotion saved in database");

            return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/{proID}")
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable int proID, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO existingPromotion = promotionDAO.find(proID);
        if (existingPromotion != null) {
            // Giữ nguyên proStatus nếu không có yêu cầu thay đổi từ phía client
            promotionDTO.setProID(proID); // Đảm bảo proID đúng
            promotionDTO.setProStatus(existingPromotion.getProStatus()); // Giữ lại giá trị proStatus gốc
            promotionDAO.update(promotionDTO); // Thực hiện cập nhật
            return new ResponseEntity<>(promotionDTO, HttpStatus.OK);
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
    public List<PromotionDTO> searchPromotions(@RequestParam String term) {
        return promotionDAO.searchPromotions(term); // Thực hiện tìm kiếm trong DAO
    }





}
