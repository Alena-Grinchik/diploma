package by.ukrop.diploma.service;

import by.ukrop.diploma.peristance.entity.Dish;
import by.ukrop.diploma.peristance.entity.Order;
import by.ukrop.diploma.peristance.entity.OrderItem;

public interface OrderItemService {
    void addOrderItem(OrderItem orderItem);

    OrderItem getByOrderAndDish(Order order, Dish dish);

    void removeOrderItem(Long id);

    void updateOrderItemQuantity(Long id, Long delta);
}
