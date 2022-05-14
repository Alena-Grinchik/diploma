package by.ukrop.diploma.controller;

import by.ukrop.diploma.Translator;
import by.ukrop.diploma.persistence.entity.Address;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.User;
import by.ukrop.diploma.service.OrderStatus;
import by.ukrop.diploma.service.PaymentMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        User user = (User) model.getAttribute("currentUser");
        if (user != null){
            model.addAttribute("lastAddress", addressService.getLastUserAddress(user));
        }
        return "checkOut";
    }

    @PostMapping("/checkOut")
    public String checkOut(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "phoneNumber") String phoneNumber,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "street") String street,
                                 @RequestParam(value = "building") String building,
                                 @RequestParam(value = "enclosure") String enclosure,
                                 @RequestParam(value = "entrance") String entrance,
                                 @RequestParam(value = "apartment") String apartment,
                                 @RequestParam(value = "floor") String floor,
                                 @RequestParam(value = "code") String code,
                                 @RequestParam(value = "orderComment") String orderComment,
                                 @RequestParam(value = "paymentMethod") String paymentMethod,
                                 HttpServletRequest request,
                                 HttpServletResponse httpResponse,
                                 Model model

    ) throws IOException {
        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");
        Pattern emailPattern = Pattern.compile("^(?:[A-Za-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");
        Pattern streetPattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ\\- .]+$");
        Pattern buildingPattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ\\- .]+$");
        Pattern entrancePattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ]+$");
        Pattern apartmentPattern = Pattern.compile("^[0-9]+$");

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
        if (!streetPattern.matcher(street).matches()){
            errors.add(Translator.toLocale("validation.street"));
        }
        if (!buildingPattern.matcher(building).matches()){
            errors.add(Translator.toLocale("validation.building"));
        }
        if (!entrancePattern.matcher(entrance).matches()){
            errors.add(Translator.toLocale("validation.entrance"));
        }
        if (!apartmentPattern.matcher(apartment).matches()){
            errors.add(Translator.toLocale("validation.apartment"));
        }
        if (!(paymentMethod != null && (paymentMethod.equals("cash") || paymentMethod.equals("card")))){
            errors.add(Translator.toLocale("validation.paymentmethod"));
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return this.checkOut(request, httpResponse, model);
        }

        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        PaymentMethod paymentMethodEnum;
        if (paymentMethod.equals("cash")) {
            paymentMethodEnum = PaymentMethod.CASH;
        } else {
            paymentMethodEnum = PaymentMethod.CARD;
        }
        Order currentOrder = orderService.getOrder(currentCartId);
        Address address = new Address();
        address.setApartment(apartment);
        address.setBuilding(building);
        address.setCode(code);
        address.setEnclosure(enclosure);
        address.setEntrance(entrance);
        address.setFloor(floor);
        address.setStreet(street);
        User user = (User) model.getAttribute("currentUser");
        address.setUser(user);
        Long currentAddressId = addressService.addAddress(address);
        address.setId(currentAddressId);
        currentOrder.setAddress(address);

        currentOrder.setStatus(OrderStatus.SUBMITTED);
        currentOrder.setDate(LocalDateTime.now());
        currentOrder.setEmail(email);
        currentOrder.setFirstName(firstName);
        currentOrder.setLastName(lastName);
        currentOrder.setPhoneNumber(phoneNumber);
        currentOrder.setPaymentMethod(paymentMethodEnum);
        currentOrder.setOrderComment(orderComment);

        orderService.updateOrder(currentOrder);
        session.removeAttribute("CurrentCart");
        System.out.println("/orderStatus/"+currentCartId);
        httpResponse.sendRedirect("/orderStatus/"+currentCartId);
        return null;
    }
}
