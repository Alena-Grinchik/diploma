package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Category;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.service.CategoryService;
import by.ukrop.diploma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/*https://stackoverflow.com/questions/7360784/add-attributes-to-the-model-of-all-controllers-in-spring-3/21233819#21233819*/

@Controller
public class SuperController {

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected OrderService orderService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @ModelAttribute("currentOrderSize")
    public int getCurrentOrderSize(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        if (currentCartId!=null) {
            Order currentOrder = orderService.getOrder(currentCartId);
            return currentOrder.getOrderItemsList().size();
        }
        else {
            return 0;
        }
    }
}
