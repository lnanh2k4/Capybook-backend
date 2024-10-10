package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Book;
import fa24.swp391.se1802.group3.capybook.models.ImportStock;
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
    public void save(ImportStock importStock) {
        entityManager.persist(importStock);
    }

    @Override
    public ImportStock find(int ISID) {
        return entityManager.find(ImportStock.class,ISID);
    }

    @Override
    public void update(ImportStock importStock) {
        entityManager.merge(importStock);
    }

    @Override
    public void delete(int ISID) {
        entityManager.remove(this.find(ISID));
    }

    @Override
    public List<ImportStock> findAll() {
        TypedQuery<ImportStock> query = entityManager.createQuery("From ImportStock", ImportStock.class);
        return query.getResultList();
    }
}
