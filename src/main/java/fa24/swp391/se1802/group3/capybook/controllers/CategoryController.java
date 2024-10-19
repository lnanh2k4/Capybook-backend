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



    @GetMapping("/{catID}")
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
    public CategoryDTO addCategory(@RequestBody CategoryDTO category){
        System.out.println("Received category data: " + category); // Log dữ liệu category được nhận
        return categoryDAO.save(category);
    }


    @PutMapping("/")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO category){
        return categoryDAO.save(category);
    }

    @PutMapping("/{catID}/soft-delete")
    public ResponseEntity<?> softDeleteCategory(@PathVariable int catID) {
        // Tìm category theo ID
        CategoryDTO category = categoryDAO.find(catID);

        // Kiểm tra nếu không tìm thấy category
        if (category == null) {
            throw new CategoryExceptionNotFound();
        }

        // Kiểm tra xem category này có child nào không
        List<CategoryDTO> childCategories = categoryDAO.findByParentCatID(catID);

        // Nếu tồn tại child, không cho phép xóa
        if (!childCategories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete this category as it has child categories.");
        }

        // Nếu không có child, thực hiện soft delete (set catStatus = 0)
        category.setCatStatus(0);
        categoryDAO.save(category);

        return ResponseEntity.ok("Category soft deleted successfully!");
    }


    @GetMapping("/search")
    public List<CategoryDTO> searchCategories(@RequestParam("term") String term) {
        return categoryDAO.searchByName(term);
    }



}
