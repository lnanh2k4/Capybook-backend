package fa24.swp391.se1802.group3.capybook.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import fa24.swp391.se1802.group3.capybook.request.StaffRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffDAOImpl implements  StaffDAO{
    EntityManager entityManager;
    static final String DEFAULT_PASSWORD = "12345";

    @Autowired
    public StaffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    AccountDAO accountDAO;

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
            AccountDTO account = accountDAO.findByUsername(username);
            Query query = entityManager.createQuery("Select s.staffID From StaffDTO s WHERE s.username=:username");
            query.setParameter("username",account);
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
    public void update(String staff) {
        try {
            System.out.println("Staff: "+ staff);
            ObjectMapper objectMapper = new ObjectMapper();
            StaffRequest staffDTO = objectMapper.readValue(staff,StaffRequest.class);

            StaffDTO existingStaff = findByID(staffDTO.getStaffID());
            if(existingStaff!=null){
                //handle account of staff
                AccountDTO account = new AccountDTO();
                account.setDob(staffDTO.getDob());
                account.setAddress(staffDTO.getAddress());
                account.setSex(staffDTO.getSex());
                account.setFirstName(staffDTO.getFirstName());
                account.setPhone(staffDTO.getPhone());
                account.setRole(staffDTO.getRole());
                account.setLastName(staffDTO.getLastName());
                account.setEmail(staffDTO.getEmail());
                account.setPassword(existingStaff.getUsername().getPassword());
                account.setUsername(existingStaff.getUsername().getUsername());
                existingStaff.setUsername(account);

                //update staff and account
                entityManager.merge(account);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
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
    public void addStaffByString(String staff) {
        try {
            ObjectMapper obj = new ObjectMapper();
            StaffRequest request = obj.readValue(staff,StaffRequest.class);
            AccountDTO account = new AccountDTO();
            account.setUsername(request.getUsername());
            account.setDob(request.getDob());
            account.setAddress(request.getAddress());
            account.setSex(request.getSex());
            account.setEmail(request.getEmail());
            account.setPhone(request.getPhone());
            account.setRole(request.getRole());
            account.setLastName(request.getLastName());
            account.setFirstName(request.getFirstName());
            account.setAccStatus(1);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            account.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));

            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setUsername(account);

            entityManager.persist(account);
            entityManager.flush();
            entityManager.persist(staffDTO);
            entityManager.flush();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    @Transactional
    public void addStaff(StaffDTO staff) {
            entityManager.persist(staff);
            entityManager.flush();
    }

    @Override
    public List<StaffDTO> searchStaffs(String keyword) {
        List<StaffDTO> list = new ArrayList<>();
        String str = "FROM StaffDTO WHERE LOWER(username.firstName) LIKE :searchKey OR LOWER(username.lastName) LIKE :searchKey";
        TypedQuery<StaffDTO> query = entityManager.createQuery(str, StaffDTO.class);
        query.setParameter("searchKey", "%" + keyword.toLowerCase() + "%");
        for (StaffDTO staffDTO : query.getResultList()) {
            if(staffDTO.getUsername().getAccStatus()!= null && staffDTO.getUsername().getAccStatus()>0){
                list.add(staffDTO);
            }
        }
        return list;
    }
}
