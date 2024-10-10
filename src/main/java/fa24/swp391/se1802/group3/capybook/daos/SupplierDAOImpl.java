package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDAOImpl implements SupplierDAO {
    EntityManager entityManager;

    public SupplierDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Supplier supplier) {
        entityManager.persist(supplier);
    }

    @Override
    public Supplier find(int supID) {
        return entityManager.find(Supplier.class,supID);
    }

    @Override
    public void update(Supplier supplier) {
        entityManager.merge(supplier);
    }

    @Override
    public void delete(int supID) {
        entityManager.remove(this.find(supID));
    }

    @Override
    public List<Supplier> findAll() {
        TypedQuery<Supplier> query = entityManager.createQuery("From Supplier", Supplier.class);
        return query.getResultList();

    }
}