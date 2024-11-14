package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.ImportStockDAO;
import fa24.swp391.se1802.group3.capybook.models.ImportStockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/importStock")
@CrossOrigin(origins = "http://localhost:5173")
public class ImportStockController {
    ImportStockDAO importStockDAO;
    @Autowired
    public ImportStockController(ImportStockDAO categoryDAO) {
        this.importStockDAO = categoryDAO;
    }

    @GetMapping("/")
    public List<ImportStockDTO> getStocksList(){
        return importStockDAO.findAll();
    }


}
