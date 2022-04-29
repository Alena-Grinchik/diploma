package by.ukrop.diploma.peristance.dao;

import by.ukrop.diploma.peristance.entity.Dish;
import by.ukrop.diploma.peristance.entity.Order;
import by.ukrop.diploma.peristance.entity.OrderItem;

public interface OrderItemDAO {
    void addOrderItem(OrderItem orderItem);

    OrderItem getByOrderAndDish(Order order, Dish dish);

    void removeOrderItem(Long id);

    OrderItem getOrderItem(Long id);
}
