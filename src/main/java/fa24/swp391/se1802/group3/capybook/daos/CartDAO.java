package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.CartDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import java.util.List;

public interface CartDAO {
    void addBookToCart(String username, int bookID, int quantity);
    List<CartDTO> viewCart(String username);
    void editQuantity(String username, int bookID, int quantity);
    void deleteBookFromCart(String username, int cartID);
}
