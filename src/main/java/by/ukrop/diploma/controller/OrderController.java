package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OrderController extends SuperController{

    @GetMapping("/orderStatus/{id}")
    public String order (@PathVariable("id") Long id, Model model, HttpServletResponse httpResponse) throws IOException {
        Order currentOrder = orderService.getOrder(id);
        if (currentOrder==null || currentOrder.getStatus().equals("draft")){
            httpResponse.sendRedirect("/");
            return null;
        }
        model.addAttribute("currentOrder", currentOrder);
        return "orderStatus";
    }
}
