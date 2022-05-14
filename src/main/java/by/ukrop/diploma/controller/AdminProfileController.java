package by.ukrop.diploma.controller;

import by.ukrop.diploma.Translator;
import by.ukrop.diploma.persistence.entity.Role;
import by.ukrop.diploma.persistence.entity.User;
import by.ukrop.diploma.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminProfileController extends SuperController{

    @Autowired
    private RoleService roleService;

    @GetMapping("/adminProfile")
    public String adminProfile(HttpServletRequest request, HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }

        model.addAttribute("allUsers",userService.getAllUsers());
        return "adminProfile";
    }

    @PostMapping("/changeUser")
    public String changeUser(@RequestParam(value = "firstName") String firstName,
                          @RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "phoneNumber") String phoneNumber,
                          @RequestParam(value = "roleId") Long roleId,
                          @RequestParam(value = "userId") Long userId,
                          HttpServletRequest request,
                          HttpServletResponse httpResponse,
                          Model model

    ) throws IOException {
        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");

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

        Role role = roleService.getRole(roleId);
        if (role == null) {
            errors.add(Translator.toLocale("validation.role"));
        }

        User user = userService.getUser(userId);
        if (user == null) {
            errors.add(Translator.toLocale("validation.user"));
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);
            userService.updateUser(user);
        }

        return this.adminProfile(request, httpResponse, model);
    }
}
