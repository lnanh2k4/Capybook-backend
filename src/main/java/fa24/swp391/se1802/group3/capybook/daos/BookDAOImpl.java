package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(BookDTO bookDTO) {
        if (bookDTO.getBookID() == null) {
            entityManager.persist(bookDTO); // Thêm mới nếu chưa có ID
        } else {
            entityManager.merge(bookDTO); // Cập nhật nếu đã có ID
        }
    }

    @Override
    public BookDTO find(int bookID) {
        return entityManager.find(BookDTO.class, bookID);
    }

    @Override
    @Transactional
    public void update(BookDTO bookDTO) {
        entityManager.merge(bookDTO); // Cập nhật thông tin sách
    }

    @Override
    @Transactional
    public void delete(int bookID) {
        BookDTO book = this.find(bookID);
        if (book != null) {
            entityManager.remove(book); // Xóa cuốn sách nếu tồn tại
        }
    }

    @Override
    public List<BookDTO> findAll() {
        TypedQuery<BookDTO> query = entityManager.createQuery("FROM BookDTO", BookDTO.class);
        return query.getResultList();
    }

    @Override
    public List<CategoryDTO> findAllCategory() {
        TypedQuery<CategoryDTO> query = entityManager.createQuery("FROM CategoryDTO", CategoryDTO.class);
        return query.getResultList();
    }
}
