package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.ImportStock;

import java.util.List;

public interface ImportStockDAO {
    void save(ImportStock importStock);
    ImportStock find(int ISID);
    void update(ImportStock importStock);
    void delete(int ISID);
    List<ImportStock> findAll();
}
