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
        String jpql = "SELECT c FROM CartDTO c WHERE c.username.username = :username";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        List<CartDTO> cartList = query.getResultList();

        // In ra console để kiểm tra
        cartList.forEach(cart -> {
            System.out.println("CartID: " + cart.getCartID());
            System.out.println("Username: " + cart.getUsername().getUsername());
            System.out.println("BookID: " + cart.getBookID().getBookID());
            System.out.println("BookTitle: " + cart.getBookID().getBookTitle());
            System.out.println("Quantity: " + cart.getQuantity());
            System.out.println("Price: " + cart.getBookID().getBookPrice());
            System.out.println("Image: " + cart.getBookID().getImage());
        });

        return cartList;
    }


    @Override
    @Transactional
    public void editQuantity(String username, int bookID, int quantity) {
        String jpql = "FROM CartDTO WHERE username.username = :username AND bookID.bookID = :bookID";
        TypedQuery<CartDTO> query = entityManager.createQuery(jpql, CartDTO.class);
        query.setParameter("username", username);
        query.setParameter("bookID", bookID);

        List<CartDTO> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            CartDTO cartItem = resultList.get(0);
            cartItem.setQuantity(quantity); // Cập nhật số lượng
            entityManager.merge(cartItem);  // Lưu thay đổi
        } else {
            throw new RuntimeException("Cart item with bookID " + bookID + " not found for username " + username);
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
