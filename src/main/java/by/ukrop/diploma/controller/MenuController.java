package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Dish;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.OrderItem;
import by.ukrop.diploma.service.DishService;
import by.ukrop.diploma.service.OrderItemService;
import by.ukrop.diploma.service.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
