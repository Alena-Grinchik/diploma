package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Discount;
import by.ukrop.diploma.persistence.entity.Role;
import by.ukrop.diploma.persistence.entity.User;
import by.ukrop.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class RegistrationController extends SuperController{

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "phoneNumber") String phoneNumber,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "passwordCheck") String passwordCheck,
                                 HttpServletResponse httpResponse,
                                 Model model,
                                 PasswordEncoder passwordEncoder

    ) throws IOException {
        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");
        Pattern emailPattern = Pattern.compile("^(?:[A-Za-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");
        List<String> errors = new ArrayList<>();

        if (!firstNamePattern.matcher(firstName).matches()){
            errors.add("Проверьте правильность введенного имени");
        }
        if (!lastNamePattern.matcher(lastName).matches()){
            errors.add("Проверьте правильность введенной фамилии");
        }
        if (!phoneNumberPattern.matcher(phoneNumber).matches()){
            errors.add("Проверьте правильность введенного номера телефона");
        }
        if (!emailPattern.matcher(email).matches()){
            errors.add("Проверьте правильность введенного email");
        }
        if (!password.equals(passwordCheck) ||
                password.length() < 8){
            errors.add("Пароль слишком короткий или не совпадает");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);

        Role role = new Role();
        role.setId(1L);
        user.setRole(role);
        Discount discount = new Discount();
        discount.setId(1L);
        user.setDiscount(discount);

        try {
            userService.addUser(user);
        } catch (Exception e) {
            errors.add("Пользователь с таким email уже существует");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("regErrors", errors);
            return "registration";
        }
        httpResponse.sendRedirect("/signIn");
        return null;
    }
}

