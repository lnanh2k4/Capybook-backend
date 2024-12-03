package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.PromotionLogDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PromotionLogDAOIml implements PromotionLogDAO {
    private final EntityManager entityManager;

    @Autowired
    public PromotionLogDAOIml(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(PromotionLogDTO promotionLogDTO) {
        entityManager.merge(promotionLogDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionLogDTO> findAll() {
        TypedQuery<PromotionLogDTO> query = entityManager.createQuery("From PromotionLogDTO", PromotionLogDTO.class);
        return query.getResultList();
    }
}
