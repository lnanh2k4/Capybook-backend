package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.ImportStockDTO;

import java.util.List;

public interface ImportStockDAO {
    void save(ImportStockDTO importStockDTO);
    ImportStockDTO find(int ISID);
    void update(ImportStockDTO importStockDTO);
    void delete(int ISID);
    List<ImportStockDTO> findAll();
}
