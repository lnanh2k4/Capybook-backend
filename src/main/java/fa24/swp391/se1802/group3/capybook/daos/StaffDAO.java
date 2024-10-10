package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Account;
import fa24.swp391.se1802.group3.capybook.models.Staff;

import java.util.List;

public interface StaffDAO {
    void save(Staff staff);
    Staff find(int staffID);
    void update(Staff staff);
    void delete(int staffID);
    List<Staff> findAll();
}
