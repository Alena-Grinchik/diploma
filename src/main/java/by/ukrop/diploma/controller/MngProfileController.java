package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.service.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MngProfileController extends SuperController{

    @GetMapping("/mngProfile")
    public String mngProfile(HttpServletRequest request, HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }
        model.addAttribute("ordersInProgress", orderService.ordersInProgress());
        return "mngProfile";
    }

    @PostMapping("/orderVerify")
    public RedirectView orderVerify (@RequestParam(value = "orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        order.setStatus(OrderStatus.APPROVED);
        orderService.updateOrder(order);

        return new RedirectView("/mngProfile");
    }

    @PostMapping("/orderDeliver")
    public RedirectView orderDeliver (@RequestParam(value = "orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        orderService.updateOrder(order);

        return new RedirectView("/mngProfile");
    }
}
