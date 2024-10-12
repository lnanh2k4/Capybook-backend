package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDTO;
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
    public void save(OrderDTO orderDTO) {
        entityManager.persist(orderDTO);
    }

    @Override
    public OrderDTO find(int orderID) {
        return entityManager.find(OrderDTO.class,orderID);
    }

    @Override
    @Transactional
    public void update(OrderDTO orderDTO) {
        entityManager.merge(orderDTO);
    }

    @Override
    public void delete(int orderID) {
        entityManager.remove(this.find(orderID));
    }

    @Override
    public List<OrderDTO> findAll() {
        TypedQuery<OrderDTO> query = entityManager.createQuery("From OrderDTO", OrderDTO.class);
        return query.getResultList();
    }
}
