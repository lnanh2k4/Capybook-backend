package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryDAOlmpl implements CategoryDAO{
    EntityManager entityManager;
    //inject entity manager using constructor injection

    @Autowired
    public CategoryDAOlmpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implements method
    @Override
    @Transactional
    public void save(CategoryDTO category) {
        entityManager.persist(category);
    }

    @Override
    public CategoryDTO find(int catID) {
        return entityManager.find(CategoryDTO.class,catID);
    }

    @Override
    @Transactional
    public void update(CategoryDTO category) {
        entityManager.merge(category);
    }

    @Override
    public void delete(int catID) {
        entityManager.remove(this.find(catID));
    }

    @Override
    public List<CategoryDTO> findAll() {
        TypedQuery<CategoryDTO> query = entityManager.createQuery("From CategoryDTO", CategoryDTO.class);
        return query.getResultList();
    }
}
