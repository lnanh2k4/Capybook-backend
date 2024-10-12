package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionResponseDTO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    AccountDAO accountDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping("/")
    public List<AccountDTO> getAccounts(){
        return accountDAO.findAll();
    }

    @GetMapping("/{username}")
    public AccountDTO getAccount(@PathVariable String username){
        if(accountDAO.find(username)!=null)
        return accountDAO.find(username);
    else throw new AccountExceptionNotFound();
    }

    @ExceptionHandler
    public ResponseEntity<AccountExceptionResponseDTO> handlerException(AccountExceptionNotFound exec){
        AccountExceptionResponseDTO error = new AccountExceptionResponseDTO();
        error.setStatus(404);
        error.setMessage(exec.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public AccountDTO addAccount(AccountDTO account){
        return accountDAO.save(account);
    }

    @PutMapping("/")
    public AccountDTO updateAccount(@RequestBody AccountDTO account){
        return accountDAO.save(account);
    }

    @DeleteMapping("/{username}")
    public String deleteAccount(@PathVariable String username){
        AccountDTO acc = accountDAO.find(username);
        if(acc!=null){
            accountDAO.delete(username);
            return "Successfully!";
        } else{
            throw new AccountExceptionNotFound();
        }
    }
}
