package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void save(AccountDTO accountDTO);
    AccountDTO findByUsername(String username);
    void deleteByUsername(String username);
    List<AccountDTO> findAll();
    public AccountDTO findDetailByUsernameAndStaff(String username, StaffDTO staff);
    public void addAccount(AccountDTO account);
    List<AccountDTO> searchAccounts(String username);
}
