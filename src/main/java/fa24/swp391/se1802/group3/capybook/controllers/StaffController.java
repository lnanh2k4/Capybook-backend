package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {
    private final StaffDAO staffDAO;

    @Autowired
    public StaffController(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    // Endpoint to fetch a single staff member by ID
    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable int id) {
        StaffDTO staff = staffDAO.findByID(id);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // New endpoint to fetch all staff members
    @GetMapping("/")
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> staffList = staffDAO.findAll();
        return ResponseEntity.ok(staffList);
    }
}
