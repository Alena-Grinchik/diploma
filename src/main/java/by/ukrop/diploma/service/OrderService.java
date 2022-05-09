package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.User;

import java.util.List;

public interface OrderService {
    Order getOrder(Long id);

    Long addOrder(Order order);

    void updateOrder(Order order);

    Order getLastUserCart(User user);

    List<Order> ordersInProgress();
}
