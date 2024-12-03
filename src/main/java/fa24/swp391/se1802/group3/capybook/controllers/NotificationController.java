package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.NotificationDAO;
import fa24.swp391.se1802.group3.capybook.models.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    NotificationDAO notificationDAO;

    @Autowired
    public NotificationController(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @GetMapping("/")
    public List<NotificationDTO> getNotifications() {
        return notificationDAO.findAll();
    }

    @GetMapping("/detail/{notID}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable int notID) {
        NotificationDTO notificationDTO = notificationDAO.find(notID);
        return notificationDTO != null ? ResponseEntity.ok(notificationDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{notTitle}")
    public List<NotificationDTO> getNotificationByTitle(@PathVariable String notTitle) {
        return notificationDAO.search(notTitle);
    }

    @PostMapping(value = "/")
    public ResponseEntity<NotificationDTO> addNotification(@RequestPart("notification") String notification){
       try {
           System.out.println("Json: " + notification);
          ObjectMapper objectMapper = new ObjectMapper();
           System.out.println("Reading value...");
         NotificationDTO notificationDTO = objectMapper.readValue(notification,NotificationDTO.class);
           System.out.println("Staff name: " + notificationDTO.getStaffID());
           System.out.println("Title: " + notificationDTO.getNotTitle());
           System.out.println("Description:" + notificationDTO.getNotDescription());
           System.out.println("Receiver: " + notificationDTO.getReceiver());

            notificationDAO.save(notificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(notificationDTO);
        } catch (JsonProcessingException e) {
           System.out.println("JsonProcessingException: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }



    @DeleteMapping("/{notID}")
    public ResponseEntity<String> deleteNotification(@PathVariable Integer notID){
        System.out.println("Notification ID: " + notID);
        if(notificationDAO.find(notID)!=null) {
                notificationDAO.delete(notID);
        return new ResponseEntity<>("Notification deleted successfully!",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
