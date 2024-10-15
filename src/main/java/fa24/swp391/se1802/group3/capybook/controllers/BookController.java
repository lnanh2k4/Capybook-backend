package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.BookDAO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("/")
    public List<BookDTO> getBooksList() {
        return bookDAO.findAll();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable int bookId) {
        BookDTO book = bookDAO.find(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy sách
        }
    }

    @PostMapping("/")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO book) {
        bookDAO.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int bookId, @RequestBody BookDTO book) {
        BookDTO existingBook = bookDAO.find(bookId);
        if (existingBook != null) {
            book.setBookID(bookId); // Đảm bảo cập nhật đúng cuốn sách
            bookDAO.update(book);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy sách
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        BookDTO book = bookDAO.find(bookId);
        if (book != null) {
            bookDAO.delete(bookId);
            return ResponseEntity.ok("Book deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }
}
