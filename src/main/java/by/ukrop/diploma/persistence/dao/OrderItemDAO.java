package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Dish;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.OrderItem;

public interface OrderItemDAO {
    void addOrderItem(OrderItem orderItem);

    OrderItem getByOrderAndDish(Order order, Dish dish);

    void removeOrderItem(Long id);

    OrderItem getOrderItem(Long id);
}
