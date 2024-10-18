package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionResponseDTO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.PromotionDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @PostMapping(value = "/")
    public ResponseEntity<AccountDTO> addAccount(@RequestPart("account") String account) {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            AccountDTO accountDTO = objectMapper.readValue(account, AccountDTO.class);

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
            existingAccount.setRole(accountDTO.getRole());
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

    @DeleteMapping("/{username}")
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
            if (account.getRole() > 1) {
                StaffDTO staff = staffDAO.findStaff(account);
                try {
                    StaffDTO manager = staffDAO.findManager(account);
                    return ResponseEntity.ok(accountDAO.findDetailByUsernameAndStaff(username, manager));
                } catch (Exception e) {
                    System.out.println("Chay zo chua");
                    return ResponseEntity.ok(accountDAO.findDetailByUsernameAndStaff(username, staff));
                }
            } else {
                return ResponseEntity.ok(account);
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
