package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Category;

import java.util.List;

public interface CategoryDAO {
    void save(Category category);
    Category find(int catID);
    void update(Category category);
    void delete(int catID);
    List<Category> findAll();
}
