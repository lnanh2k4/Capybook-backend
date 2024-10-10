package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO{

    EntityManager entityManager;

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Order find(int orderID) {
        return entityManager.find(Order.class,orderID);
    }

    @Override
    @Transactional
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(int orderID) {
        entityManager.remove(this.find(orderID));
    }

    @Override
    public List<Order> findAll() {
        TypedQuery<Order> query = entityManager.createQuery("From Order", Order.class);
        return query.getResultList();
    }
}
