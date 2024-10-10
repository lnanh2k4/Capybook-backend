package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDetailDAOImpl implements OrderDetailDAO{
    EntityManager entityManager;

    @Autowired
    public OrderDetailDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(OrderDetail orderDetail) {
        entityManager.persist(orderDetail);
    }

    @Override
    public OrderDetail find(int ODID) {
        return entityManager.find(OrderDetail.class,ODID);
    }

    @Override
    @Transactional
    public void update(OrderDetail orderDetail) {
        entityManager.merge(orderDetail);
    }

    @Override
    public void delete(int ODID) {
        entityManager.remove(this.find(ODID));
    }

    @Override
    public List<OrderDetail> findAll() {
        TypedQuery<OrderDetail> query = entityManager.createQuery("From OrderDetail", OrderDetail.class);
        return query.getResultList();
    }
}
