package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.daos.StaffDAO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.StaffDTO;
import fa24.swp391.se1802.group3.capybook.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staffs")
public class StaffController {
    private final StaffDAO staffDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    public StaffController(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    // Endpoint to fetch a single staff member by ID
    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable int id) {
        StaffDTO staff = staffDAO.findByID(id);
        if (staff != null) {
            StaffResponse staffResponse = new StaffResponse();
            staffResponse.setUsername(staff.getUsername().getUsername());
            staffResponse.setDob(staff.getUsername().getDob());
            staffResponse.setEmail(staff.getUsername().getEmail());
            staffResponse.setRole(staff.getUsername().getRole());
            staffResponse.setAddress(staff.getUsername().getAddress());
            staffResponse.setFirstName(staff.getUsername().getFirstName());
            staffResponse.setLastName(staff.getUsername().getLastName());
            staffResponse.setPhone(staff.getUsername().getPhone());
            staffResponse.setSex(staff.getUsername().getSex());
            staffResponse.setStaffID(staff.getStaffID());
            return ResponseEntity.ok(staffResponse);
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

    @GetMapping("/username/{username}")
    public ResponseEntity<StaffDTO> getStaff(@PathVariable String username) {
        System.out.println("Input: " + username);
        AccountDTO account = accountDAO.findByUsername(username);
        if (account != null) {
            StaffDTO staffDTO = staffDAO.findStaff(account);
            return ResponseEntity.status(HttpStatus.OK).body(staffDTO);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
