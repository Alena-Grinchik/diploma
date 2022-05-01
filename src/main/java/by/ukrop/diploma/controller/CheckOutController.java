package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Address;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.service.AddressService;
import by.ukrop.diploma.service.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Controller
public class CheckOutController extends SuperController{

    @Autowired
    private AddressService addressService;

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

    @PostMapping("/checkOut")
    public RedirectView checkOut(@RequestParam(value = "firstName") String firstName,
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
                                 HttpServletRequest request

    ){
        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");
        Pattern emailPattern = Pattern.compile("^(?:[a-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");
        Pattern streetPattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ\\- .]+$");
        Pattern buildingPattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ\\- .]+$");
        Pattern entrancePattern = Pattern.compile("^[0-9A-Za-zА-Яа-яёЁ]+$");
        Pattern apartmentPattern = Pattern.compile("^[0-9]+$");

        if (
                !firstNamePattern.matcher(firstName).matches() ||
                !lastNamePattern.matcher(lastName).matches() ||
                !phoneNumberPattern.matcher(phoneNumber).matches() ||
                !emailPattern.matcher(email).matches() ||
                !streetPattern.matcher(street).matches() ||
                !buildingPattern.matcher(building).matches() ||
                !entrancePattern.matcher(entrance).matches() ||
                !apartmentPattern.matcher(apartment).matches() ||
                !(paymentMethod != null && (paymentMethod.equals("cash") || paymentMethod.equals("card")))
        ){
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(!firstNamePattern.matcher(firstName).matches());
            System.out.println(!lastNamePattern.matcher(lastName).matches());
            System.out.println(!phoneNumberPattern.matcher(phoneNumber).matches());
            System.out.println(!emailPattern.matcher(email).matches());
            System.out.println(!streetPattern.matcher(street).matches());
            System.out.println(!buildingPattern.matcher(building).matches());
            System.out.println(!entrancePattern.matcher(entrance).matches());
            System.out.println(!apartmentPattern.matcher(apartment).matches());
            System.out.println(!(paymentMethod != null && (paymentMethod.equals("cash") || paymentMethod.equals("card"))));
            System.out.println("----------------------------------------------------------------------------------------");
            return new RedirectView("/checkOutError");
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
        //address.setUser();
        Long currentAddressId = addressService.addAddress(address);
        address.setId(currentAddressId);
        currentOrder.setAddress(address);

        currentOrder.setStatus("verified");
        currentOrder.setDate(LocalDateTime.now());
        currentOrder.setEmail(email);
        currentOrder.setFirstName(firstName);
        currentOrder.setLastName(lastName);
        currentOrder.setPhoneNumber(phoneNumber);
        currentOrder.setPaymentMethod(paymentMethodEnum);
        currentOrder.setOrderComment(orderComment);

        orderService.addOrder(currentOrder);
        session.removeAttribute("CurrentCart");

        return new RedirectView("/");
    }
}
