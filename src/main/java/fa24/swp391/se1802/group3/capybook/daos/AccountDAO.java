package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;

import java.util.List;

public interface AccountDAO {
    AccountDTO save(AccountDTO accountDTO);
    AccountDTO find(String username);
    void delete(String username);
    List<AccountDTO> findAll();
}
