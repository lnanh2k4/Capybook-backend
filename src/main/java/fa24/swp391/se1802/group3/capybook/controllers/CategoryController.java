package fa24.swp391.se1802.group3.capybook.controllers;


import fa24.swp391.se1802.group3.capybook.daos.CategoryDAO;
import fa24.swp391.se1802.group3.capybook.exceptions.CategoryExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.exceptions.CategoryExceptionResponseDTO;
import fa24.swp391.se1802.group3.capybook.models.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    CategoryDAO categoryDAO;

    @Autowired
    public CategoryController(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @GetMapping("/")
        public List<CategoryDTO> getCategoriesList(){
            return categoryDAO.findAll();
        }

    @GetMapping("/{username}")
    public CategoryDTO getCategory(@PathVariable int catID){
        if(categoryDAO.find(catID)!=null)
            return categoryDAO.find(catID);
        else throw new CategoryExceptionNotFound();
    }

    @ExceptionHandler
    public ResponseEntity<CategoryExceptionResponseDTO> handlerException(CategoryExceptionNotFound exec){
        CategoryExceptionResponseDTO error = new CategoryExceptionResponseDTO();
        error.setStatus(404);
        error.setMessage(exec.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public CategoryDTO addCategory(CategoryDTO category){
        return categoryDAO.save(category);
    }

    @PutMapping("/")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO category){
        return categoryDAO.save(category);
    }

    @DeleteMapping("/{catid}")
    public String deleteCategory(@PathVariable int catID){
        CategoryDTO cat = categoryDAO.find(catID);
        if(cat!=null){
            categoryDAO.delete(catID);
            return "Successfully!";
        } else{
            throw new CategoryExceptionNotFound();
        }
    }
}
