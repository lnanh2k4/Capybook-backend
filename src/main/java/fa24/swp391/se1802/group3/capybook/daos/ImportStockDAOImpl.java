package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.ImportStockDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImportStockDAOImpl implements ImportStockDAO  {
    EntityManager entityManager;

    @Autowired
    public ImportStockDAOImpl(EntityManager entityManager) { this.entityManager = entityManager; }


    @Override
    public void save(ImportStockDTO importStockDTO) {
        entityManager.persist(importStockDTO);
    }

    @Override
    public ImportStockDTO find(int ISID) {
        return entityManager.find(ImportStockDTO.class,ISID);
    }

    @Override
    public void update(ImportStockDTO importStockDTO) {
        entityManager.merge(importStockDTO);
    }

    @Override
    public void delete(int ISID) {
        entityManager.remove(this.find(ISID));
    }

    @Override
    public List<ImportStockDTO> findAll() {
        TypedQuery<ImportStockDTO> query = entityManager.createQuery("From ImportStockDTO", ImportStockDTO.class);
        return query.getResultList();
    }
}
