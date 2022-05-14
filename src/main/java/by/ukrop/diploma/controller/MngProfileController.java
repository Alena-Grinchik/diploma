package by.ukrop.diploma.controller;

import by.ukrop.diploma.Translator;
import by.ukrop.diploma.persistence.entity.Address;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.service.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class MngProfileController extends SuperController{

    @GetMapping("/mngProfile")
    public String mngProfile(HttpServletResponse httpResponse, Model model) throws IOException {
        if (model.getAttribute("currentUser") == null){
            httpResponse.sendRedirect("/signIn");
            return null;
        }
        model.addAttribute("ordersInProgress", orderService.ordersInProgress());
        return "mngProfile";
    }

    @PostMapping("/orderVerify")
    public RedirectView orderVerify (@RequestParam(value = "orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        order.setStatus(OrderStatus.APPROVED);
        orderService.updateOrder(order);

        return new RedirectView("/mngProfile");
    }

    @PostMapping("/orderDeliver")
    public RedirectView orderDeliver (@RequestParam Long orderId){
        Order order = orderService.getOrder(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        orderService.updateOrder(order);

        return new RedirectView("/mngProfile");
    }

    @PostMapping("/mngRemoveItem")
    public RedirectView mngRemoveItem (@RequestParam Long orderItemId){
        orderItemService.removeOrderItem(orderItemId);
        return new RedirectView("/mngProfile");
    }

    @PostMapping("/mngUpdateQuantity")
    public RedirectView mngUpdateQuantity (@RequestParam Long orderItemId, @RequestParam Long delta){
        orderItemService.updateOrderItemQuantity(orderItemId,delta);
        return new RedirectView("/mngProfile");
    }

    @PostMapping("/mngUpdateDetails")
    public String mngUpdateDetails (@RequestParam Long orderId, @RequestParam String firstName,
                                          @RequestParam String lastName, @RequestParam String phoneNumber,
                                          @RequestParam String street, @RequestParam String building,
                                          @RequestParam String enclosure, @RequestParam String entrance,
                                          @RequestParam String apartment, @RequestParam String floor,
                                          @RequestParam String code,  @RequestParam String orderComment,
                                          Model model, HttpServletResponse httpResponse) throws IOException {

        Pattern firstNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern lastNamePattern = Pattern.compile("^[A-Za-zА-Яа-яёЁ\\- ]+$");
        Pattern phoneNumberPattern = Pattern.compile("^(\\d{2}|[(]?[0-9]{2}[)])(\\d{7})$");
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

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return this.mngProfile(httpResponse, model);
        }

        Order updOrder = orderService.getOrder(orderId);
        updOrder.setFirstName(firstName);
        updOrder.setLastName(lastName);
        updOrder.setPhoneNumber(phoneNumber);
        updOrder.setOrderComment(orderComment);

        Address updAddress = updOrder.getAddress();
        updAddress.setStreet(street);
        updAddress.setBuilding(building);
        updAddress.setEnclosure(enclosure);
        updAddress.setEntrance(entrance);
        updAddress.setApartment(apartment);
        updAddress.setFloor(floor);
        updAddress.setCode(code);

        addressService.updateAddress(updAddress);
        orderService.updateOrder(updOrder);

        return this.mngProfile(httpResponse, model);
    }
}
