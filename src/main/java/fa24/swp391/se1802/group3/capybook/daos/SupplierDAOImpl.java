package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.SupplierDTO;
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
    public void save(SupplierDTO supplierDTO) {
        entityManager.persist(supplierDTO);
    }

    @Override
    public SupplierDTO find(int supID) {
        return entityManager.find(SupplierDTO.class,supID);
    }

    @Override
    public void update(SupplierDTO supplierDTO) {
        entityManager.merge(supplierDTO);
    }

    @Override
    public void delete(int supID) {
        entityManager.remove(this.find(supID));
    }

    @Override
    public List<SupplierDTO> findAll() {
        TypedQuery<SupplierDTO> query = entityManager.createQuery("From SupplierDTO", SupplierDTO.class);
        return query.getResultList();

    }
}