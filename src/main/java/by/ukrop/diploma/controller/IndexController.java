package by.ukrop.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController extends SuperController{

    @GetMapping("/")
    public String hello() {
        return "index";
    }
}
