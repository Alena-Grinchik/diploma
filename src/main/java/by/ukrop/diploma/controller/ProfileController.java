package by.ukrop.diploma.controller;

import by.ukrop.diploma.Translator;
import by.ukrop.diploma.persistence.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class ProfileController extends SuperController{

    @GetMapping("/profile")
    public String profile(HttpServletRequest request, HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }
        return "profile";
    }

    @PostMapping("/profile")
    public String profile(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "phoneNumber") String phoneNumber,
                                 @RequestParam(value = "email") String email,
                                 HttpServletRequest request,
                                 HttpServletResponse httpResponse,
                                 Model model

    ) throws IOException {
        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");
        Pattern emailPattern = Pattern.compile("^(?:[A-Za-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");

        List<String> errors = new ArrayList<>();

        if (!firstNamePattern.matcher(firstName).matches()){
            errors.add(Translator.toLocale("validation.firstname"));
        }

        if (!lastNamePattern.matcher(lastName).matches()){
            errors.add(Translator.toLocale("validation.lastname"));
        }

        if (!phoneNumberPattern.matcher(phoneNumber).matches()){
            errors.add(Translator.toLocale("validation.phone"));
        }

        if (!emailPattern.matcher(email).matches()){
            errors.add(Translator.toLocale("validation.email"));
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
        } else {
            User currentUser = (User) model.getAttribute("currentUser");
            assert currentUser != null;
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setPhoneNumber(phoneNumber);
            currentUser.setEmail(email);
            userService.updateUser(currentUser);
        }

        return this.profile(request, httpResponse, model);
    }
}
