package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;

import java.util.List;

public interface CategoryDAO {
    void save(CategoryDTO category);
    CategoryDTO find(int catID);
    void update(CategoryDTO category);
    void delete(int catID);
    List<CategoryDTO> findAll();
}
