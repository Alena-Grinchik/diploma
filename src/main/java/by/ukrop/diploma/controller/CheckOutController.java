package by.ukrop.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CheckOutController extends SuperController{

    @GetMapping("/checkOut")
    public String checkOut(HttpServletRequest request, HttpServletResponse httpResponse, Model model) throws IOException {
        if (getCurrentOrderSize(request)==0){
            httpResponse.sendRedirect("/menu");
            return null;
        }
        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        model.addAttribute("currentOrder", orderService.getOrder(currentCartId));
        return "checkOut";
    }
}
