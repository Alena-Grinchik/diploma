package by.ukrop.diploma.service;

import by.ukrop.diploma.entity.Dish;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.entity.OrderItem;

public interface OrderItemService {
    void addOrderItem(OrderItem orderItem);

    OrderItem getByOrderAndDish(Order order, Dish dish);

    void removeOrderItem(Long id);

    void updateOrderItemQuantity(Long id, Long delta);
}
