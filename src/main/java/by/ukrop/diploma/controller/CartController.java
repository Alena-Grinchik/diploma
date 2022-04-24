package by.ukrop.diploma.controller;

import by.ukrop.diploma.entity.Category;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.service.CategoryService;
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
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/cart")
    public String menu(Model model, HttpServletRequest request) {
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        HttpSession session = request.getSession(true);

        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        Order currentOrder;

        if (currentCartId!=null) {
            currentOrder = orderService.getOrder(currentCartId);
            model.addAttribute("currentOrderSize", currentOrder.getOrderItemsList().size());
            if (currentOrder.getOrderItemsList().isEmpty()){
                return "emptyCart";
            }
        }
        else {
            currentOrder = new Order();
            model.addAttribute("currentOrderSize", 0);
            model.addAttribute("currentOrder",currentOrder);
            return "emptyCart";
        }
        model.addAttribute("currentOrder",currentOrder);
        return "cart";
    }

    @PostMapping("/removeItem")
    public RedirectView removeItem (@RequestParam(value = "orderItemId") Long orderItemId, HttpServletRequest request){
        orderItemService.removeOrderItem(orderItemId);
        return new RedirectView("/cart");
    }

    @PostMapping("/updateQuantity")
    public RedirectView updateItemQuantity (@RequestParam(value = "orderItemId") Long orderItemId, @RequestParam(value = "delta") Long delta, HttpServletRequest request){
        orderItemService.updateOrderItemQuantity(orderItemId,delta);
        return new RedirectView("/cart");
    }
}
