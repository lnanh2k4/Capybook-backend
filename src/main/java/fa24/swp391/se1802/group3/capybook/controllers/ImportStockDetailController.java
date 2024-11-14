package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.ImportStockDetailDAO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<ImportStockDetailDTO>> getImportStockDetailsByStockId(@PathVariable int id) {
        List<ImportStockDetailDTO> details = importStockDetailDAO.findByImportStockId(id);
        if (details != null && !details.isEmpty()) {
            return ResponseEntity.ok(details);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

