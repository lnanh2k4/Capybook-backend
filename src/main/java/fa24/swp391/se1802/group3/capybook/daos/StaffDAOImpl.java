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
        staffDTO.setStaffID(0);
        entityManager.persist(staffDTO);
    }

    @Override
    public StaffDTO findByID(int staffID) {
        StaffDTO staff = entityManager.find(StaffDTO.class, staffID);
        return staff;
    }

    @Override
    public StaffDTO findStaff(String username) {
        try {
            Query queryForAccount = entityManager.createQuery("Select a From AccountDTO a WHERE a.username=:username");
            queryForAccount.setParameter("username", username);
            AccountDTO account = (AccountDTO) queryForAccount.getSingleResult();

            Query query = entityManager.createQuery("Select s.staffID From StaffDTO s WHERE s.username=:username");
            query.setParameter("username",queryForAccount.getSingleResult());
            StaffDTO staff = new StaffDTO();
            staff.setStaffID((Integer) query.getSingleResult());
            staff.setUsername(account);
            return staff;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
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

    @Override
    @Transactional
    public void addStaff(StaffDTO staff) {
            entityManager.persist(staff);
            entityManager.flush();
    }

}
