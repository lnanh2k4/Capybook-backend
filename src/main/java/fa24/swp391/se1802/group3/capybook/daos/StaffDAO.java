package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.StaffDTO;

import java.util.List;

public interface StaffDAO {
    void save(StaffDTO staffDTO);
    StaffDTO find(int staffID);
    void update(StaffDTO staffDTO);
    void delete(int staffID);
    List<StaffDTO> findAll();
}
