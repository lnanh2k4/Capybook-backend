package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;

import java.util.List;

public interface PromotionDAO {
    void save(PromotionDTO promotionDTO);
    PromotionDTO find(int proID);
    void update(PromotionDTO promotionDTO);
    void delete(int proID);
    List<PromotionDTO> findAll();
    List<PromotionDTO> searchPromotions(String searchTerm);
}
