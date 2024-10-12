package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.CategoryDAO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
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
}
