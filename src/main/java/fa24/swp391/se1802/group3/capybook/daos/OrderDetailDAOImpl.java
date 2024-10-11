package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDetailDTO;
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
    public void save(OrderDetailDTO orderDetailDTO) {
        entityManager.persist(orderDetailDTO);
    }

    @Override
    public OrderDetailDTO find(int ODID) {
        return entityManager.find(OrderDetailDTO.class,ODID);
    }

    @Override
    @Transactional
    public void update(OrderDetailDTO orderDetailDTO) {
        entityManager.merge(orderDetailDTO);
    }

    @Override
    public void delete(int ODID) {
        entityManager.remove(this.find(ODID));
    }

    @Override
    public List<OrderDetailDTO> findAll() {
        TypedQuery<OrderDetailDTO> query = entityManager.createQuery("From OrderDetail", OrderDetailDTO.class);
        return query.getResultList();
    }
}
