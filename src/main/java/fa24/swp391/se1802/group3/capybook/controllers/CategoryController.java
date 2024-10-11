package fa24.swp391.se1802.group3.capybook.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @RequestMapping("/")
    public String getCategoryList(){
        return "category list";
    }
}
