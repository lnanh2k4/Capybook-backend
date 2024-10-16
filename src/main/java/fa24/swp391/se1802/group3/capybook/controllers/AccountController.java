package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionResponseDTO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    AccountDAO accountDAO;
    StaffDAO staffDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO, StaffDAO staffDAO) {
        this.accountDAO = accountDAO;
        this.staffDAO = staffDAO;
    }

    @Autowired


    @GetMapping("/")
    public List<AccountDTO> getAccounts() {
        return accountDAO.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String username) {
        AccountDTO account = accountDAO.findByUsername(username);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO account) {
        accountDAO.save(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{username}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable String username, @RequestBody AccountDTO account) {
        if (accountDAO.findByUsername(username) != null) {
            accountDAO.save(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteAccount(@PathVariable String username) {
        AccountDTO account = accountDAO.findByUsername(username);
        if (account != null) {
            accountDAO.deleteByUsername(username);
            return ResponseEntity.ok("Account deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/detail/{username}")
    public ResponseEntity<AccountDTO> getAccountDetail(@PathVariable String username) {
        AccountDTO account = accountDAO.findByUsername(username);
        if (account != null) {
            if(account.getRole()>1){
                StaffDTO staff = staffDAO.findStaff(account);
                try {
                    StaffDTO manager = staffDAO.findManager(account);
                    return ResponseEntity.ok(accountDAO.findDetailByUsernameAndStaff(username, manager));
                } catch (Exception e) {
                    System.out.println("Chay zo chua");
                    return ResponseEntity.ok(accountDAO.findDetailByUsernameAndStaff(username, staff));
                }
            } else{
                return ResponseEntity.ok(account);
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
