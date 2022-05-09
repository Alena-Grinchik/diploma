package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.User;

public interface OrderDAO {
    Order getOrder(Long id);

    Long addOrder(Order order);

    void updateOrder(Order order);

    Order getLastUserCart(User user);
}