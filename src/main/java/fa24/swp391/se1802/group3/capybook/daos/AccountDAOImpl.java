package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {
    final int ACTIVE_STATUS = 1;
    final int INACTIVE_STATUS = 0;

    //define entity manager
    EntityManager entityManager;
    //inject entity manager using constructor injection

    @Autowired
    public AccountDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implements method
    @Override
    @Transactional
    public void save(AccountDTO accountDTO) {
        AccountDTO account = this.findByUsername(accountDTO.getUsername());
        if (account != null) {
            entityManager.merge(accountDTO);
        } else {
            entityManager.persist(accountDTO);
        }
    }

    @Override
    public List<AccountDTO> searchAccounts(String searchKey) {
            String str = "FROM AccountDTO WHERE LOWER(username) LIKE :searchKey OR LOWER(firstName) LIKE :searchKey OR LOWER(lastName) LIKE :searchKey";
            TypedQuery<AccountDTO> query = entityManager.createQuery(str, AccountDTO.class);
            query.setParameter("searchKey", "%" + searchKey.toLowerCase() + "%");
            return query.getResultList();
    }


    @Override
    public AccountDTO findByUsername(String username) {
        Query query = entityManager.createQuery("Select a.username, a.firstName, a.lastName, a.dob, a.address, a.email, a.role, a.sex, a.phone, a.accStatus From AccountDTO a WHERE a.username=:username");
        query.setParameter("username", username);
        Object[] result = (Object[]) query.getSingleResult();
        AccountDTO account = new AccountDTO();
        account.setUsername((String) result[0]);
        account.setFirstName((String) result[1]);
        account.setLastName((String) result[2]);
        account.setDob((Date) result[3]);
        account.setAddress((String) result[4]);
        account.setEmail((String) result[5]);
        account.setRole((Integer) result[6]);
        account.setSex((Integer) result[7]);
        account.setPhone((String) result[8]);
        account.setAccStatus((Integer) result[9]);
        return account;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        Query query = entityManager.createQuery(
                "UPDATE AccountDTO SET accStatus=:accStatus Where username = :username");
        query.setParameter("accStatus", INACTIVE_STATUS);
        query.setParameter("username", username);
        query.executeUpdate();
    }

    @Override
    public List<AccountDTO> findAll() {
        TypedQuery<AccountDTO> query = entityManager.createQuery("From AccountDTO", AccountDTO.class);
        return query.getResultList();
    }

    @Override
    public AccountDTO findDetailByUsernameAndStaff(String username, StaffDTO staff) {
        Query query = entityManager.createQuery(
                "SELECT a.username, a.firstName, a.lastName, a.dob, a.email, a.phone, " +
                        "a.role, a.address, a.sex " +
                        "FROM AccountDTO a " +
                        "WHERE a.username = :username");

        query.setParameter("username", username);


        Object[] result = (Object[]) query.getSingleResult();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername((String) result[0]);
        accountDTO.setFirstName((String) result[1]);
        accountDTO.setLastName((String) result[2]);
        accountDTO.setDob((Date) result[3]);
        accountDTO.setEmail((String) result[4]);
        accountDTO.setPhone((String) result[5]);
        accountDTO.setRole((Integer) result[6]);
        accountDTO.setAddress((String) result[7]);
        accountDTO.setSex((Integer) result[8]);
        System.out.println("Staff ID: "+staff.getStaffID());
        System.out.println("Staff manager ID: "+ staff.getManagerID().getStaffID());
        Collection<StaffDTO> collection = new ArrayList<>();
        collection.add(staff);
        System.out.println(staff.getUsername().getFirstName());
        accountDTO.setStaffDTOCollection(collection);
        return accountDTO;
    }

    @Override
    @Transactional
    public void addAccount(AccountDTO account) {
        String jpql = "INSERT INTO AccountDTO (username, firstName, lastName, dob, email, phone, role, address, sex, password, accStatus) "
                + "VALUES (:username, :firstName, :lastName,  :dob, :email, :phone, :role, :address, :sex, :password, :accStatus)";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("username", account.getUsername());
        query.setParameter("firstName", account.getFirstName());
        query.setParameter("lastName", account.getLastName());
        query.setParameter("dob", account.getDob());
        query.setParameter("email", account.getEmail());
        query.setParameter("phone", account.getPhone());
        query.setParameter("role", account.getRole());
        query.setParameter("address", account.getAddress());
        query.setParameter("sex", account.getSex());
        query.setParameter("password",account.getPassword());
        query.setParameter("accStatus",ACTIVE_STATUS);
        query.executeUpdate();
    }
}
