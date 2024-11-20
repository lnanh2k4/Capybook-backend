package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CartDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {

    private final EntityManager entityManager;

    @Autowired
    public CartDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addBookToCart(String username, int bookID, int quantity) {
        // Tìm trong giỏ hàng với username và bookID
        String jpql = "FROM CartDTO WHERE username.username = :username AND bookID.bookID = :bookID";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        query.setParameter("bookID", bookID);

        CartDTO cartItem;

        if (!query.getResultList().isEmpty()) {
            // Nếu đã có trong giỏ, cập nhật số lượng
            cartItem = query.getSingleResult();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            entityManager.merge(cartItem);
        } else {
            // Nếu chưa có, thêm mới
            cartItem = new CartDTO();
            cartItem.setUsername(entityManager.find(AccountDTO.class, username));
            cartItem.setBookID(entityManager.find(BookDTO.class, bookID));
            cartItem.setQuantity(quantity);
            entityManager.persist(cartItem);
        }
    }

    @Override
    public List<CartDTO> viewCart(String username) {
        // Lấy tất cả sách trong giỏ hàng của người dùng
        String jpql = "FROM CartDTO WHERE username.username = :username";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void editQuantity(String username, int bookID, int quantity) {
        // Tìm sách trong giỏ hàng
        String jpql = "FROM CartDTO WHERE username.username = :username AND bookID.bookID = :bookID";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        query.setParameter("bookID", bookID);

        if (!query.getResultList().isEmpty()) {
            // Cập nhật số lượng nếu tìm thấy
            CartDTO cartItem = query.getSingleResult();
            cartItem.setQuantity(quantity);
            entityManager.merge(cartItem);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    @Override
    @Transactional
    public void deleteBookFromCart(String username, int bookID) {
        // Tìm sách cần xóa
        String jpql = "FROM CartDTO WHERE username.username = :username AND bookID.bookID = :bookID";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        query.setParameter("bookID", bookID);

        if (!query.getResultList().isEmpty()) {
            // Xóa nếu tìm thấy
            CartDTO cartItem = query.getSingleResult();
            entityManager.remove(cartItem);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
}
