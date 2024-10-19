package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import java.util.List;

public interface CategoryDAO {
    CategoryDTO save(CategoryDTO category);
    CategoryDTO find(int catID);
    void delete(int catID);
    List<CategoryDTO> findAll();

    // Thêm phương thức này để tìm các category con
    List<CategoryDTO> findByParentCatID(int parentCatID);

    // Thêm phương thức này để tìm kiếm category theo tên
    List<CategoryDTO> searchByName(String name);
}
