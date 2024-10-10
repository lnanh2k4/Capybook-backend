package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Promotion;

import java.util.List;

public interface PromotionDAO {
    void save(Promotion promotion);
    Promotion find(int proID);
    void update(Promotion promotion);
    void delete(int proID);
    List<Promotion> findAll();
}
