package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;

import java.util.List;

public interface StaffDAO {
    void save(StaffDTO staffDTO);
    StaffDTO findByID(int staffID);
    StaffDTO findStaff(AccountDTO username);
    StaffDTO findManager(AccountDTO username);
    void update(StaffDTO staffDTO);
    void delete(int staffID);
    List<StaffDTO> findAll();
}
