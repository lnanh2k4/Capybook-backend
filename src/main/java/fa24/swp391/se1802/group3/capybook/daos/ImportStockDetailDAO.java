package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.ImportStockDetailDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImportStockDetailDAO {

    private final EntityManager entityManager;

    @Autowired
    public ImportStockDetailDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ImportStockDetailDTO> findByImportStockId(int importStockId) {
        TypedQuery<ImportStockDetailDTO> query = entityManager.createQuery(
                "SELECT d FROM ImportStockDetailDTO d WHERE d.isid.isid = :importStockId", ImportStockDetailDTO.class);
        query.setParameter("importStockId", importStockId);
        return query.getResultList();
    }
}
