package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.NotificationDTO;

import java.util.List;

public interface NotificationDAO {

    void save(NotificationDTO notificationDTO);
    NotificationDTO find(Integer notID);
    void delete(Integer notID);
    List<NotificationDTO> findAll();
    List<NotificationDTO> search(String notTitle);
}
