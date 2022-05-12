package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Dish;
import by.ukrop.diploma.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AddItemController extends SuperController{

    @Autowired
    private DishService dishService;

    @GetMapping("/addItem")
    public String addItem(@RequestParam(required = false) Long dishId, HttpServletRequest request, HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }

        if (dishId != null) {
            Dish dish = dishService.getDish(dishId);
            model.addAttribute("dish", dish);
        }

        return "addItem";
    }
}
