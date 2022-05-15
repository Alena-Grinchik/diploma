package by.ukrop.diploma.controller;

import by.ukrop.diploma.persistence.entity.Category;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.User;
import by.ukrop.diploma.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
/*https://stackoverflow.com/questions/7360784/add-attributes-to-the-model-of-all-controllers-in-spring-3/21233819#21233819*/

@Controller
public class SuperController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected OrderItemService orderItemService;

    @Autowired
    protected AddressService addressService;

    @Autowired
    protected DiscountService discountService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @ModelAttribute("currentOrderSize")
    public int getCurrentOrderSize(HttpServletRequest request, @ModelAttribute("currentUser") User currentUser) {
        return getCurrentOrderSize(request);
    }

    public int getCurrentOrderSize(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Long currentCartId = (Long) session.getAttribute("CurrentCart");
        if (currentCartId != null) {
            Order currentOrder = orderService.getOrder(currentCartId);
            return currentOrder.getOrderItemsList().size();
        } else {
            return 0;
        }
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(Authentication authentication, HttpServletRequest request) {
        if (authentication != null) {
            User userAuth = (User) authentication.getPrincipal();
            User user = userService.getUser(userAuth.getId());
            if (!Objects.equals(user.getDiscount().getId(), user.applicableDiscountId())){
                user.setDiscount(discountService.getDiscount(user.applicableDiscountId()));
                userService.updateUser(user);
            }
            HttpSession session = request.getSession(true);
            Long currentCartId = (Long) session.getAttribute("CurrentCart");

            if (currentCartId != null) {
                Order currentOrder = orderService.getOrder(currentCartId);
                if (currentOrder.getUser() == null) {
                    currentOrder.setUser(user);
                    orderService.updateOrder(currentOrder);
                }
            } else {
                Order lastOrder = orderService.getLastUserCart(user);
                if (lastOrder != null){
                    session.setAttribute("CurrentCart", lastOrder.getId());
                }
            }
            return user;
        }
        return null;
    }
}
