package by.ukrop.diploma.controller;

import by.ukrop.diploma.entity.Category;
import by.ukrop.diploma.entity.Dish;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.entity.OrderItem;
import by.ukrop.diploma.service.CategoryService;
import by.ukrop.diploma.service.DishService;
import by.ukrop.diploma.service.OrderItemService;
import by.ukrop.diploma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/menu")
    public String menu(Model model, HttpServletRequest request) {
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        HttpSession session = request.getSession(true);

        Long currentCartId = (Long) session.getAttribute("CurrentCart");

        if (currentCartId!=null) {
            Order currentOrder = orderService.getOrder(currentCartId);
            model.addAttribute("currentOrderSize", currentOrder.getOrderItemsList().size());
        }
        else {
            model.addAttribute("currentOrderSize", 0);
        }
        return "menu";
    }

    @PostMapping("/addItem")
    public RedirectView addItem (@RequestParam(value = "dishId") Long dishId, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        Long orderId;

        if (currentCartId==null) {
            Order order = new Order();
            order.setDate(LocalDateTime.now());
            order.setStatus("draft");
            orderId = orderService.addOrder(order);
            session.setAttribute("CurrentCart", orderId);
        } else {
            orderId = currentCartId;
        }

        Order currentOrder = orderService.getOrder(orderId);
        Dish currentDish = dishService.getDish(dishId);

        OrderItem currentOrderItem = new OrderItem();
        currentOrderItem.setOrder(currentOrder);
        currentOrderItem.setDish(currentDish);
        orderItemService.addOrderItem(currentOrderItem);

        return new RedirectView("/menu");
    }
}
