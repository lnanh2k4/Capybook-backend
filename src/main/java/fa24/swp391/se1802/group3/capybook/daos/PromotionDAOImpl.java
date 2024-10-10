package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Promotion;
import jakarta.persistence.EntityManager;
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
    public void save(Promotion promotion) {
        entityManager.persist(promotion);
    }

    @Override
    public Promotion find(int proID) {
        return entityManager.find(Promotion.class,proID);
    }

    @Override
    @Transactional
    public void update(Promotion promotion) {
        entityManager.merge(promotion);
    }

    @Override
    public void delete(int proID) {
        entityManager.remove(this.find(proID));
    }

    @Override
    public List<Promotion> findAll() {
        TypedQuery<Promotion> query = entityManager.createQuery("From Promotion", Promotion.class);
        return query.getResultList();
    }
}
