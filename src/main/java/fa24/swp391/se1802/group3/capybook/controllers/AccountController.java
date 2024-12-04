package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import fa24.swp391.se1802.group3.capybook.request.ChangePasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j // cho phép sử dụng log.infor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    final int ROLE_ADMIN = 0;
    final int ROLE_CUSTOMER = 1;
    final int ROLE_SELLER_STAFF = 2;
    final int ROLE_WAREHOUSE_STAFF = 3;
    final String DEFAULT_PASSWORD = "12345";
    AccountDAO accountDAO;
    StaffDAO staffDAO;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Autowired
    public AccountController(AccountDAO accountDAO, StaffDAO staffDAO) {
        this.accountDAO = accountDAO;
        this.staffDAO = staffDAO;
    }


    @GetMapping("/")
    public List<AccountDTO> getAccounts() {
        List<AccountDTO> list = new ArrayList<>();
        for (AccountDTO accountDTO : accountDAO.findAll()) {
            if(accountDTO.getAccStatus()!=null && accountDTO.getAccStatus()!=0){
                list.add(accountDTO);
            }
        }
        return list;
    }

    @GetMapping("/search")
    public List<AccountDTO> searchAccounts(@RequestParam String keyword) {
        List<AccountDTO> list = new ArrayList<>();
        for (AccountDTO accountDTO : accountDAO.searchAccounts(keyword)) {
            if(accountDTO.getAccStatus()!=null &&accountDTO.getAccStatus()>0){
                list.add(accountDTO);
            }
        }
        return list;
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String username) {
        AccountDTO account = accountDAO.findByUsername(username);
        System.out.println(account.getAccStatus());
        if (account.getAccStatus() != null && account.getAccStatus() != 0) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<AccountDTO> addAccount(@RequestPart("account") String account) {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            AccountDTO accountDTO = objectMapper.readValue(account, AccountDTO.class);
            accountDTO.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            accountDAO.addAccount(accountDTO);
            if(accountDTO.getRole() == ROLE_ADMIN || accountDTO.getRole() == ROLE_SELLER_STAFF || accountDTO.getRole() == ROLE_WAREHOUSE_STAFF){
                staffDAO.save(accountDTO);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AccountDTO> registeredAccount(@RequestPart("register") String account) {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            AccountDTO accountDTO = objectMapper.readValue(account, AccountDTO.class);
            accountDTO.setRole(ROLE_CUSTOMER);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
            accountDAO.addAccount(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<AccountDTO> updateAccount( @PathVariable String username,  @RequestPart("account") String account) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountDTO accountDTO = objectMapper.readValue(account, AccountDTO.class);

            AccountDTO existingAccount = accountDAO.findByUsername(username);
            if (existingAccount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            existingAccount.setUsername(accountDTO.getUsername());
            existingAccount.setFirstName(accountDTO.getFirstName());
            existingAccount.setLastName(accountDTO.getLastName());
            if(accountDTO.getRole()!=null){
                existingAccount.setRole(accountDTO.getRole());
            }
            existingAccount.setSex(accountDTO.getSex());
            existingAccount.setPhone(accountDTO.getPhone());
            existingAccount.setEmail(accountDTO.getEmail());
            existingAccount.setAddress(accountDTO.getAddress());
            existingAccount.setDob(accountDTO.getDob());

            accountDAO.save(existingAccount);

            return ResponseEntity.ok(existingAccount);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/change")
    public ResponseEntity<AccountDTO> changePassword(  @RequestPart("account") String passwordRequest) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChangePasswordRequest request = objectMapper.readValue( passwordRequest, ChangePasswordRequest.class);

            AccountDTO existingAccount = accountDAO.findByUsername(request.getUsername());
            if (existingAccount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            existingAccount.setPassword(request.getPassword());

            accountDAO.save(existingAccount);

            return ResponseEntity.ok(existingAccount);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteAccount(@PathVariable String username) {
        AccountDTO account = accountDAO.findByUsername(username);
        if (account != null) {
            if(account.getRole()!=1){
                staffDAO.delete(staffDAO.findStaff(username).getStaffID());
            } else{
                accountDAO.deleteByUsername(username);
            }
            return ResponseEntity.ok("Account deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }


}
