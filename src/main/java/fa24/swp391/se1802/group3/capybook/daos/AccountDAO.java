package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Account;

import java.util.List;

public interface AccountDAO {
    void save(Account account);
    Account find(String username);
    void update(Account account);
    void delete(String username);
    List<Account> findAll();
}
