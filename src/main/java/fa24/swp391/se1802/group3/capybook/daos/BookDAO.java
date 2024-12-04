package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;

import java.util.List;

public interface BookDAO {
    void save(BookDTO bookDTO);
    BookDTO find(int bookID);
    void update(BookDTO bookDTO);
    void delete(int bookID);
    List<BookDTO> findAll();
    List<BookDTO> searchBooks(String searchTerm);
    List<BookDTO> findBooksByTitleAndAuthorAndPublisher(String bookTitle, String author, String publisher);
    List<BookDTO> sortBooks(String sortBy, String sortOrder);
    List<BookDTO> filterBooksByCategory(int categoryID);

}
