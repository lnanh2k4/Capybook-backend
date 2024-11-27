package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.BookCategoryDTO;

import java.util.List;

public interface BookCategoryDAO {
    void save(BookCategoryDTO bookCategoryDTO);
    BookCategoryDTO find(int bookCateId);
    void update(BookCategoryDTO bookCategoryDTO);
    void delete(int bookCateId);
    List<BookCategoryDTO> findAll();
    List<BookCategoryDTO> findByBookId(int bookId); // Tìm các danh mục của một sách
}
