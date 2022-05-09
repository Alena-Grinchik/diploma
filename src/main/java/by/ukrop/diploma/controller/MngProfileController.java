package by.ukrop.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
