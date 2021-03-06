package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Dish;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.OrderItem;
import by.ukrop.diploma.service.DishService;
import by.ukrop.diploma.service.OrderItemService;
import by.ukrop.diploma.service.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class MenuController extends SuperController{

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @PostMapping("/deleteMenuItem")
    public RedirectView deleteMenuItem (@RequestParam(value = "dishId") Long dishId) {
        Dish dish = dishService.getDish(dishId);
        String anchor = dish.getCategory().getAnchorName();

        dishService.deleteDish(dishId);
        return new RedirectView("/menu#"+anchor);
    }

    @GetMapping("/addMenuItem")
    public String addMenuItem(@RequestParam(required = false) Long dishId, HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }

        if (dishId != null) {
            Dish dish = dishService.getDish(dishId);
            model.addAttribute("dish", dish);
        }

        return "addMenuItem";
    }

    @PostMapping("/addMenuItem")
    public String addMenuItem(@RequestParam(required = false) Long dishId, @RequestParam String dishNameEng, @RequestParam String dishDescriptionEng,
                              @RequestParam String dishName, @RequestParam String dishDescription, @RequestParam String dishUrl, @RequestParam Long dishCategory,
                              @RequestParam Double dishPrice,HttpServletResponse httpResponse, Model model) throws IOException {
        Dish dish;
        Long savedDishId;

        if (dishId != null) {
            dish = dishService.getDish(dishId);
        } else {
            dish = new Dish();
        }

        dish.setDescription(dishDescription);
        dish.setImage(dishUrl);
        dish.setDescriptionEng(dishDescriptionEng);
        dish.setDescription(dishDescription);
        dish.setName(dishName);
        dish.setNameEng(dishNameEng);
        dish.setPrice(dishPrice);
        dish.setCategory(categoryService.getCategory(dishCategory));

        if (dishId != null) {
            dishService.updateDish(dish);
            savedDishId=dishId;
        } else {
            savedDishId=dishService.addDish(dish);
        }

        return addMenuItem(savedDishId,httpResponse,model);
    }

    @PostMapping("/addItem")
    public RedirectView addItem (@RequestParam(value = "dishId") Long dishId, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        Long orderId;

        if (currentCartId==null) {
            Order order = new Order();
            order.setDate(LocalDateTime.now());
            order.setStatus(OrderStatus.DRAFT);
            orderId = orderService.addOrder(order);
            session.setAttribute("CurrentCart", orderId);
        } else {
            orderId = currentCartId;
        }

        Order currentOrder = orderService.getOrder(orderId);
        Dish currentDish = dishService.getDish(dishId);
        OrderItem currentOrderItem = orderItemService.getByOrderAndDish(currentOrder, currentDish);

        if (currentOrderItem!=null){
            currentOrderItem.setQuantity(currentOrderItem.getQuantity()+1);
        } else {
            currentOrderItem = new OrderItem();
            currentOrderItem.setOrder(currentOrder);
            currentOrderItem.setDish(currentDish);
            currentOrderItem.setQuantity(1L);
        }
        orderItemService.addOrderItem(currentOrderItem);
        return new RedirectView("/menu#dish-"+dishId);
    }
}
