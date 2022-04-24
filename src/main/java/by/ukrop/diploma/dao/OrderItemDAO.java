package by.ukrop.diploma.dao;

import by.ukrop.diploma.entity.Dish;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.entity.OrderItem;

public interface OrderItemDAO {
    void addOrderItem(OrderItem orderItem);

    OrderItem getByOrderAndDish(Order order, Dish dish);


}
