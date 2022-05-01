package by.ukrop.diploma.controller;

import by.ukrop.diploma.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CartController extends SuperController{

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/cart")
    public String menu(Model model, HttpServletRequest request) {

         if (getCurrentOrderSize(request)==0){
             return "emptyCart";
         } else {
             HttpSession session = request.getSession(true);
             Long currentCartId = (Long) session.getAttribute("CurrentCart");
             model.addAttribute("currentOrder", orderService.getOrder(currentCartId));
             return "cart";
         }
    }

    @PostMapping("/removeItem")
    public RedirectView removeItem (@RequestParam(value = "orderItemId") Long orderItemId){
        orderItemService.removeOrderItem(orderItemId);
        return new RedirectView("/cart");
    }

    @PostMapping("/updateQuantity")
    public RedirectView updateItemQuantity (@RequestParam(value = "orderItemId") Long orderItemId, @RequestParam(value = "delta") Long delta){
        orderItemService.updateOrderItemQuantity(orderItemId,delta);
        return new RedirectView("/cart");
    }
}
