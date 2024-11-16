package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.ImportStockDAO;
import fa24.swp391.se1802.group3.capybook.daos.ImportStockDetailDAO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDTO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDetailDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/importStock")
@CrossOrigin(origins = "http://localhost:5173")
public class ImportStockController {
    private final ImportStockDAO importStockDAO;
    private final ImportStockDetailDAO importStockDetailDAO;

    @Autowired
    public ImportStockController(ImportStockDAO importStockDAO, ImportStockDetailDAO importStockDetailDAO) {
        this.importStockDAO = importStockDAO;
        this.importStockDetailDAO = importStockDetailDAO;
    }

    @GetMapping("/")
    public List<ImportStockDTO> getStocksList() {
        return importStockDAO.findAll();
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<ImportStockDTO> createImportStock(@RequestBody ImportStockDTO importStockDTO) {
        ImportStockDTO savedStock = importStockDAO.save(importStockDTO);

        // Kiểm tra null cho collection trước khi lặp
        if (importStockDTO.getImportStockDetailCollection() != null) {
            for (ImportStockDetailDTO detail : importStockDTO.getImportStockDetailCollection()) {
                detail.setIsid(savedStock);  // Gán stock ID
                importStockDetailDAO.save(detail);
            }
        }

        return ResponseEntity.ok(savedStock);
    }


}
