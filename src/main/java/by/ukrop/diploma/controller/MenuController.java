package by.ukrop.diploma.controller;

import by.ukrop.diploma.entity.Category;
import by.ukrop.diploma.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/menu")
    public String menu(Model model) {
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "menu";
    }
}
