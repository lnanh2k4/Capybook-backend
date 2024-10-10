package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Account;
import fa24.swp391.se1802.group3.capybook.models.Book;
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
    public void save(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book find(int bookID) {
        return entityManager.find(Book.class,bookID);
    }

    @Override
    @Transactional
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(int bookID) {
        entityManager.remove(this.find(bookID));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("From Book", Book.class);
        return query.getResultList();
    }
}
