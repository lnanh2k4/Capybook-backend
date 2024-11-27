package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.BookDAO;
import fa24.swp391.se1802.group3.capybook.models.BookCategoryDTO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    private final BookDAO bookDAO;

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("/")
    @Transactional
    public List<BookDTO> getBooksList() {
        List<BookDTO> books = bookDAO.findAll();
        // Khởi tạo thủ công `bookCategories` cho mỗi sách
        books.forEach(book -> Hibernate.initialize(book.getBookCategories()));
        return books;
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable int bookId) {
        BookDTO book = bookDAO.find(bookId);
        if (book != null) {
            Hibernate.initialize(book.getBookCategories()); // Khởi tạo `bookCategories`
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping
    @Transactional
    public ResponseEntity<BookDTO> addBook(
            @RequestPart("book") String bookData,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookDTO book = objectMapper.readValue(bookData, BookDTO.class);

            book.setBookStatus(1);

            List<BookDTO> existingBooks = bookDAO.findBooksByTitleAndAuthorAndPublisher(
                    book.getBookTitle(), book.getAuthor(), book.getPublisher());
            if (!existingBooks.isEmpty()) {
                BookDTO existingBook = existingBooks.get(0);
                existingBook.setBookQuantity(
                        (existingBook.getBookQuantity() == null ? 0 : existingBook.getBookQuantity())
                                + (book.getBookQuantity() == null ? 0 : book.getBookQuantity())
                );
                bookDAO.update(existingBook);
                return ResponseEntity.ok(existingBook);
            }

            bookDAO.save(book);

            if (book.getBookCategories() != null && !book.getBookCategories().isEmpty()) {
                for (BookCategoryDTO bookCategory : book.getBookCategories()) {
                    CategoryDTO category = entityManager.find(CategoryDTO.class, bookCategory.getCatId().getCatID());
                    if (category == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                    }
                    bookCategory.setBookId(book);
                    entityManager.persist(bookCategory);
                }
            }

            if (image != null && !image.isEmpty()) {
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String uniqueFileName = "book_" + book.getBookID() + "_" + System.currentTimeMillis() + ".jpg";
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Path targetPath = uploadPath.resolve(uniqueFileName);
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                book.setImage("/uploads/" + uniqueFileName);
                bookDAO.update(book);
            }

            Hibernate.initialize(book.getBookCategories()); // Khởi tạo thủ công trước khi trả về
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{bookId}")
    @Transactional
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable int bookId,
            @RequestPart("book") String bookData,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookDTO book = objectMapper.readValue(bookData, BookDTO.class);

            BookDTO existingBook = bookDAO.find(bookId);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            existingBook.setBookTitle(book.getBookTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setTranslator(book.getTranslator());
            existingBook.setHardcover(book.getHardcover());
            existingBook.setDimension(book.getDimension());
            existingBook.setWeight(book.getWeight());
            existingBook.setBookPrice(book.getBookPrice());
            existingBook.setBookDescription(book.getBookDescription());
            existingBook.setBookStatus(book.getBookStatus());
            existingBook.setBookQuantity(book.getBookQuantity());

            List<BookCategoryDTO> oldCategories = existingBook.getBookCategories();
            if (oldCategories != null) {
                for (BookCategoryDTO bookCategory : oldCategories) {
                    entityManager.remove(bookCategory);
                }
            }

            if (book.getBookCategories() != null && !book.getBookCategories().isEmpty()) {
                for (BookCategoryDTO bookCategory : book.getBookCategories()) {
                    CategoryDTO category = entityManager.find(CategoryDTO.class, bookCategory.getCatId().getCatID());
                    if (category == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                    }
                    bookCategory.setBookId(existingBook);
                    entityManager.persist(bookCategory);
                }
            }

            if (image != null && !image.isEmpty()) {
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String uniqueFileName = "book_" + bookId + "_" + System.currentTimeMillis() + ".jpg";
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Path targetPath = uploadPath.resolve(uniqueFileName);
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                existingBook.setImage("/uploads/" + uniqueFileName);
            }

            bookDAO.update(existingBook);

            Hibernate.initialize(existingBook.getBookCategories()); // Khởi tạo thủ công
            return ResponseEntity.ok(existingBook);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
