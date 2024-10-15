package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO{
    EntityManager entityManager;

    @Autowired
    public BookDAOImpl(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public void save(BookDTO bookDTO) {
        entityManager.persist(bookDTO);
    }

    @Override
    public BookDTO find(int bookID) {
        return entityManager.find(BookDTO.class,bookID);
    }

    @Override
    @Transactional
    public void update(BookDTO bookDTO) {
        entityManager.merge(bookDTO);
    }

    @Override
    public void delete(int bookID) {
        entityManager.remove(this.find(bookID));
    }

    @Override
    public List<BookDTO> findAll() {
        TypedQuery<BookDTO> query = entityManager.createQuery("From BookDTO", BookDTO.class);
        return query.getResultList();
    }
}
