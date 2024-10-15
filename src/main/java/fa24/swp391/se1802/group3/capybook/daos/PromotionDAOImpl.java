package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PromotionDAOImpl implements PromotionDAO {
    EntityManager entityManager;

    @Autowired
    public PromotionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(PromotionDTO promotionDTO) {
        entityManager.persist(promotionDTO);
    }

    @Override
    public PromotionDTO find(int proID) {
        // Tìm PromotionDTO bằng ID
        return entityManager.find(PromotionDTO.class, proID);
    }

    @Override
    @Transactional
    public void update(PromotionDTO promotionDTO) {
        entityManager.merge(promotionDTO);
    }

    @Transactional
    @Override
    public void delete(int proID) {
        // Tạo truy vấn JPQL để xóa khuyến mãi dựa trên proID
        Query query = entityManager.createQuery("DELETE FROM PromotionDTO p WHERE p.proID = :proID");
        query.setParameter("proID", proID);
        int result = query.executeUpdate();  // Trả về số dòng bị ảnh hưởng
        if (result == 0) {
            throw new EntityNotFoundException("Promotion not found with proID: " + proID);
        }
    }



    @Override
    public List<PromotionDTO> findAll() {
        TypedQuery<PromotionDTO> query = entityManager.createQuery("From PromotionDTO", PromotionDTO.class);
        return query.getResultList();
    }
}
