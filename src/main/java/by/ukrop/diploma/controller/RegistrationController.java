package by.ukrop.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController extends SuperController{

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
}
