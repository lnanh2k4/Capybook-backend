package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StaffDAOImpl implements  StaffDAO{
    EntityManager entityManager;

    @Autowired
    public StaffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(StaffDTO staffDTO) {
        entityManager.persist(staffDTO);
    }

    @Override
    public StaffDTO find(int staffID) {
        return entityManager.find(StaffDTO.class,staffID);
    }

    @Override
    @Transactional
    public void update(StaffDTO staffDTO) {
        entityManager.merge(staffDTO);
    }

    @Override
    public void delete(int staffID) {
        entityManager.remove(this.find(staffID));
    }

    @Override
    public List<StaffDTO> findAll() {
        TypedQuery<StaffDTO> query = entityManager.createQuery("From StaffDTO", StaffDTO.class);
        return query.getResultList();
    }
}
