package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.BookDAO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
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
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            BookDTO book = objectMapper.readValue(bookData, BookDTO.class);
            System.out.println("Parsed book data: " + book);

            // Đặt trạng thái bookStatus là 1
            book.setBookStatus(1);

            // Lưu thông tin sách
            bookDAO.save(book);
            Integer bookID = book.getBookID(); // Lấy bookID sau khi lưu

            if (image != null && !image.isEmpty()) {
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = "book_" + bookID + "_" + System.currentTimeMillis() + extension;

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
                bookDAO.save(book);
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

            // Tìm sách hiện có trong cơ sở dữ liệu
            BookDTO existingBook = bookDAO.find(bookId);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu sách không tồn tại
            }

            // Cập nhật thông tin sách (ngoại trừ ảnh)
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

            if (image != null && !image.isEmpty()) {
                // Tạo một tên file độc nhất với bookID
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // Lấy đuôi file (ví dụ: .jpg, .png)

                // Sử dụng bookID và ngày giờ hiện tại để tạo tên file độc nhất
                String uniqueFileName = "book_" + bookId + "_" + System.currentTimeMillis() + extension;

                System.out.println("Generated unique file name: " + uniqueFileName);

                // Xác định thư mục lưu file
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);

                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    System.out.println("Directory created: " + uploadDir);
                }

                // Lưu file ảnh vào thư mục
                try (InputStream inputStream = image.getInputStream()) {
                    Path targetPath = uploadPath.resolve(uniqueFileName);
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File saved at: " + targetPath);
                }

                // Cập nhật đường dẫn của ảnh mới trong đối tượng sách
                existingBook.setImage("/uploads/" + uniqueFileName);
                System.out.println("Image path updated in book object");
            }

            // Cập nhật sách trong cơ sở dữ liệu
            bookDAO.update(existingBook);
            System.out.println("Book updated in database");

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
