package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

public class CategoryDAOlmp implements CategoryDAO{
    EntityManager entityManager;
    //inject entity manager using constructor injection

    @Autowired
    public CategoryDAOlmp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implements method
    @Override
    @Transactional
    public void save(Category category){
        entityManager.persist(category);
    }

    @Override
    public Category find(int catID) {
        return entityManager.find(Category.class,catID);
    }

    @Override
    @Transactional
    public void update(Category category) {
        entityManager.merge(category);
    }

    @Override
    public void delete(int catID) {
        entityManager.remove(this.find(catID));
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("From Category", Category.class);
        return query.getResultList();
    }
}
