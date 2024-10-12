package fa24.swp391.se1802.group3.capybook.controllers;

<<<<<<< HEAD
import fa24.swp391.se1802.group3.capybook.daos.CategoryDAO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
=======
import fa24.swp391.se1802.group3.capybook.daos.BookDAO;
import fa24.swp391.se1802.group3.capybook.daos.CategoryDAO;
import fa24.swp391.se1802.group3.capybook.models.BookDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import jdk.jfr.Category;
>>>>>>> f5210612228d5c45a397fb43ef6760cb467e3fcc
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
<<<<<<< HEAD
    public String getCategoryList(){
        CategoryDAO categoryDAO;

    @Autowired
    public CategoryController(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @GetMapping("/")
        public List<CategoryDTO> getCategorissList(){
            return categoryDAO.findAll();
        }
=======
    CategoryDAO categoryDAO;
    @Autowired
    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @GetMapping("/")
    public List<CategoryDTO> getCategorysList(){
        return categoryDAO.findAll();
    }
>>>>>>> f5210612228d5c45a397fb43ef6760cb467e3fcc
}
