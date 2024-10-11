package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.SupplierDTO;

import java.util.List;

public interface SupplierDAO {
    void save(SupplierDTO supplierDTO);
    SupplierDTO find(int supID);
    void update(SupplierDTO supplierDTO);
    void delete(int supID);
        List<SupplierDTO> findAll();
}
