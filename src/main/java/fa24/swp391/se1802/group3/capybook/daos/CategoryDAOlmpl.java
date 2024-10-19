package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.exceptions.CategoryExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CategoryDAOlmpl implements CategoryDAO {
    EntityManager entityManager;

    @Autowired
    public CategoryDAOlmpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        if (categoryDTO.getCatName() == null || categoryDTO.getCatName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return entityManager.merge(categoryDTO);
    }

    @Override
    public CategoryDTO find(int catID) {
        CategoryDTO object = entityManager.find(CategoryDTO.class, catID);
        if (object != null) {
            return object;
        } else {
            throw new CategoryExceptionNotFound();
        }
    }

    @Override
    @Transactional
    public void delete(int catID) {
        entityManager.remove(this.find(catID));
    }

    @Override
    public List<CategoryDTO> findAll() {
        TypedQuery<CategoryDTO> query = entityManager.createQuery("SELECT c FROM CategoryDTO c", CategoryDTO.class);
        return query.getResultList();
    }

    @Override
    public List<CategoryDTO> findByParentCatID(int parentCatID) {
        TypedQuery<CategoryDTO> query = entityManager.createQuery(
                "SELECT c FROM CategoryDTO c WHERE c.parentCatID = :parentCatID", CategoryDTO.class);
        query.setParameter("parentCatID", parentCatID);
        return query.getResultList();
    }

    @Override
    public List<CategoryDTO> searchByName(String name) {
        TypedQuery<CategoryDTO> query = entityManager.createQuery(
                "SELECT c FROM CategoryDTO c WHERE LOWER(c.catName) LIKE LOWER(:name)", CategoryDTO.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
