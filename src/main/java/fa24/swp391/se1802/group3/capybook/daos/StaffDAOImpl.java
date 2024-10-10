package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Account;
import fa24.swp391.se1802.group3.capybook.models.Staff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StaffDAOImpl implements  StaffDAO{
    EntityManager entityManager;

    public StaffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Staff staff) {
        entityManager.persist(staff);
    }

    @Override
    public Staff find(int staffID) {
        return entityManager.find(Staff.class,staffID);
    }

    @Override
    public void update(Staff staff) {
        entityManager.merge(staff);
    }

    @Override
    public void delete(int staffID) {
        entityManager.remove(this.find(staffID));
    }

    @Override
    public List<Staff> findAll() {
        TypedQuery<Staff> query = entityManager.createQuery("From Staff", Staff.class);
        return query.getResultList();
    }
}
