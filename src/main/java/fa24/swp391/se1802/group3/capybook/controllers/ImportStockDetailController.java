package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.ImportStockDetailDAO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDTO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/importStock")
@CrossOrigin(origins = "http://localhost:5173")
public class ImportStockDetailController {

    private final ImportStockDetailDAO importStockDetailDAO;

    @Autowired
    public ImportStockDetailController(ImportStockDetailDAO importStockDetailDAO) {
        this.importStockDetailDAO = importStockDetailDAO;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getImportStockDetailsByStockId(@PathVariable int id) {
        try {
            List<ImportStockDetailDTO> details = importStockDetailDAO.findByImportStockId(id);

            if (details == null || details.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No details found for import stock ID: " + id);
            }

            return ResponseEntity.ok(details);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching details: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<?> addImportStockDetails(@PathVariable int id, @RequestBody List<ImportStockDetailDTO> details) {
        try {
            System.out.println("Received JSON: " + details);
            for (ImportStockDetailDTO detail : details) {
                System.out.println("Before save - Quantity: " + detail.getISDQuantity() + ", Price: " + detail.getImportPrice());
                detail.setIsid(new ImportStockDTO(id));
                importStockDetailDAO.save(detail);
                System.out.println("Saved detail: " + detail);
            }
            return ResponseEntity.ok("Details added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }






}
