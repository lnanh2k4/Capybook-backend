package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Supplier;

import java.util.List;

public interface SupplierDAO {
    void save(Supplier supplier);
    Supplier find(int supID);
    void update(Supplier supplier);
    void delete(int supID);
        List<Supplier> findAll();
}
