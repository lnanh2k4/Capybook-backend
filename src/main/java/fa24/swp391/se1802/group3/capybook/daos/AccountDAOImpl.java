package fa24.swp391.se1802.group3.capybook.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import fa24.swp391.se1802.group3.capybook.utils.RandomNumberGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    final int UNVERIFIED_STATUS = 3;
    final String DEFAULT_PASSWORD = "12345";
    //define entity manager
    EntityManager entityManager;
    PasswordEncoder password = new BCryptPasswordEncoder(10);


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
        try {
            Query query = entityManager.createQuery("Select a.username, a.firstName, a.lastName, a.dob, a.address, a.email, a.role, a.sex, a.phone, a.accStatus, a.password From AccountDTO a WHERE a.username=:username");
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
            account.setPassword((String) result[10]);
            return account;
        } catch (Exception e) {
            System.out.println("No found account with username = " + username);
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        AccountDTO account = this.findByUsername(username);
        if (account.getRole() != 1) {
            account.setAccStatus(0);
            entityManager.merge(account);
        } else {
            try {
                TypedQuery<StaffDTO> query = entityManager.createQuery(
                        "SELECT s FROM StaffDTO s WHERE s.username.username = :username", StaffDTO.class
                );
                query.setParameter("username", username);
                StaffDTO staff = query.getSingleResult();
                entityManager.remove(staff);
                account.setAccStatus(0);
                entityManager.merge(account);
            } catch (Exception e) {
                System.out.println("Error in findStaff: " + e.getMessage());
            }

        }

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
        System.out.println("Staff ID: " + staff.getStaffID());
        Collection<StaffDTO> collection = new ArrayList<>();
        collection.add(staff);
        System.out.println(staff.getUsername().getFirstName());
        accountDTO.setStaffDTOCollection(collection);
        return accountDTO;
    }

    @Override
    @Transactional
    public void addAccount(String account) {
        try {
            //Init object mapper
            ObjectMapper obj = new ObjectMapper();
            //Convert String to account object
            AccountDTO accountDTO = obj.readValue(account, AccountDTO.class);
            accountDTO.setAccStatus(UNVERIFIED_STATUS);
            accountDTO.setPassword(password.encode(DEFAULT_PASSWORD));
            RandomNumberGenerator random = new RandomNumberGenerator();
            String number = random.generateNumber();
            accountDTO.setCode(number);
            entityManager.persist(accountDTO);
            if (accountDTO.getRole() != 1) {
                StaffDTO staff = new StaffDTO();
                staff.setUsername(accountDTO);
                entityManager.persist(staff);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void registerAccount(String account) {
        try {
            //Init object mapper
            ObjectMapper obj = new ObjectMapper();
            //Convert String to account object
            AccountDTO accountDTO = obj.readValue(account, AccountDTO.class);
            accountDTO.setAccStatus(UNVERIFIED_STATUS);
            accountDTO.setPassword(password.encode(accountDTO.getPassword()));
            RandomNumberGenerator random = new RandomNumberGenerator();
            String number = random.generateNumber();
            accountDTO.setCode(number);
            entityManager.persist(accountDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean verifyAccount(String username, String code) {
        AccountDTO account = this.findByUsername(username);
        return account.getCode().equals(code);
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        AccountDTO account = this.findByUsername(username);
        if(account.getPassword().equals(password.encode(currentPassword))){
            account.setPassword(password.encode(newPassword));
            entityManager.merge(account);
        }
    }

    @Override
    public void setPassword(String username, String currentPassword) {
        AccountDTO accountDTO = this.findByUsername(username);
        accountDTO.setAccStatus(1);
        accountDTO.setPassword(password.encode(currentPassword));
        entityManager.merge(accountDTO);
    }
}
