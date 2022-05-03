package by.ukrop.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController extends SuperController{

    @GetMapping("/signIn")
    public String signIn() {
        return "signIn";
    }
}
