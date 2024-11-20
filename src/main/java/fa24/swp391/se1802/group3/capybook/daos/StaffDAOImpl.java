package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    @Transactional
    @Override
    public void save(AccountDTO accountDTO) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setUsername(accountDTO);
        entityManager.persist(staffDTO);
    }

    @Override
    public StaffDTO findByID(int staffID) {
        StaffDTO staff = entityManager.find(StaffDTO.class, staffID);
        return staff;
    }

    @Override
    public StaffDTO findStaff(AccountDTO username) {
        Query query = entityManager.createQuery("Select s.staffID From StaffDTO s WHERE s.username=:username");
        query.setParameter("username",username);

        StaffDTO staff = new StaffDTO();
        staff.setStaffID((Integer) query.getSingleResult());
        staff.setUsername(username);
        return staff;
    }

    @Override
    public StaffDTO findManager(AccountDTO username) {
        Query query = entityManager.createQuery("Select s.staffID, s.managerID From StaffDTO s WHERE s.username=:username");
        query.setParameter("username",username);
        Object[] result = (Object[]) query.getSingleResult();

        StaffDTO staff = new StaffDTO();
        staff.setStaffID((Integer) result[0]);
        staff.setUsername(username);
        staff.setManagerID((StaffDTO) result[1]);
        return staff;
    }

    @Override
    @Transactional
    public void update(StaffDTO staffDTO) {
        entityManager.merge(staffDTO);
    }

    @Override
    public void delete(int staffID) {
        entityManager.remove(this.findByID(staffID));
    }

    @Override
    public List<StaffDTO> findAll() {
        TypedQuery<StaffDTO> query = entityManager.createQuery("From StaffDTO", StaffDTO.class);
        return query.getResultList();
    }
}
