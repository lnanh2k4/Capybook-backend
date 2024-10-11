package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;

import java.util.List;

public interface AccountDAO {
    void save(AccountDTO accountDTO);
    AccountDTO find(String username);
    void update(AccountDTO accountDTO);
    void delete(String username);
    List<AccountDTO> findAll();
}
