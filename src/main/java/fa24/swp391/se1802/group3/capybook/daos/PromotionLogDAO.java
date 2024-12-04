package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.PromotionLogDTO;

import java.util.Date;
import java.util.List;

public interface PromotionLogDAO {
    void save(PromotionLogDTO promotionLogDTO);
    List<PromotionLogDTO> findAll();
    List<PromotionLogDTO> findByAction(Integer action);
    List<PromotionLogDTO> findByDateRange(Date startDate, Date endDate);
}
