package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Book;

import java.util.List;

public interface BookDAO {
    void save(Book book);
    Book find(int bookID);
    void update(Book book);
    void delete(int bookID);
    List<Book> findAll();
}
