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
    public ResponseEntity<BookDTO> addBook(
            @RequestPart("book") BookDTO book,
            @RequestPart("image") MultipartFile image) {

        try {
            // Kiểm tra và lưu file ảnh nếu có
            if (image != null && !image.isEmpty()) {
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());

                // Đường dẫn lưu hình ảnh trong thư mục public/uploads
                Path uploadPath = Paths.get("public/uploads/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Tạo thư mục nếu chưa tồn tại
                }

                // Sử dụng InputStream để lưu file
                try (InputStream inputStream = image.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Lưu đường dẫn của ảnh vào cơ sở dữ liệu (chỉ lưu đường dẫn)
                    String imagePath = "/uploads/" + fileName;
                    book.setImage(imagePath);  // setImage để lưu đường dẫn vào DB
                }
            }

            // Lưu thông tin sách vào DB
            bookDAO.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
