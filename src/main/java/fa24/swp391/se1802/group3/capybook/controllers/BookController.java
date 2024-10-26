package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.BookDAO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "http://localhost:5175")
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

    @PostMapping
    public ResponseEntity<BookDTO> addBook(
            @RequestPart("book") String bookData,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookDTO book = objectMapper.readValue(bookData, BookDTO.class);

            // Ensure that the book has catID (now as an Integer)
            if (book.getCatID() == null) {
                System.out.println("Error: catID is null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Handle missing catID
            }

            // Fetch the category using catID and associate it with the book
            CategoryDTO category = entityManager.find(CategoryDTO.class, book.getCatID());
            if (category == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Handle invalid catID
            }

            book.setCatID(category.getCatID());  // Set the category reference as the Integer value
            book.setBookStatus(1);  // Assuming active status

            // Save book information
            bookDAO.save(book);

            // Handle image saving if provided
            if (image != null && !image.isEmpty()) {
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = "book_" + book.getBookID() + "_" + System.currentTimeMillis() + extension;

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
                bookDAO.save(book);  // Save the book again with the image path
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable int bookId,
            @RequestPart("book") String bookData,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            System.out.println("Request received for updating book with ID: " + bookId);

            ObjectMapper objectMapper = new ObjectMapper();
            BookDTO book = objectMapper.readValue(bookData, BookDTO.class);
            System.out.println("Parsed book data: " + book);

            // Find the existing book
            BookDTO existingBook = bookDAO.find(bookId);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Update book information, including catID
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
            existingBook.setCatID(book.getCatID()); // Ensure catID is updated

            // Handle the image upload
            if (image != null && !image.isEmpty()) {
                System.out.println("Received image file: " + image.getOriginalFilename());

                // Extract file details
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                System.out.println("Original file name: " + originalFileName);

                // Validate file extension
                if (!originalFileName.contains(".")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // No extension found
                }

                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = "book_" + bookId + "_" + System.currentTimeMillis() + extension;
                System.out.println("Generated unique file name: " + uniqueFileName);

                // Upload path
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Save the file
                try (InputStream inputStream = image.getInputStream()) {
                    Path targetPath = uploadPath.resolve(uniqueFileName);
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image saved at: " + targetPath.toString());
                }

                existingBook.setImage("/uploads/" + uniqueFileName);
            } else {
                System.out.println("No image file uploaded.");
            }

            // Update the book in the database
            bookDAO.update(existingBook);

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
