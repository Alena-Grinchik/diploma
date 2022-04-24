package by.ukrop.diploma.service;

import by.ukrop.diploma.dao.OrderItemDAO;
import by.ukrop.diploma.entity.Dish;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemDAO orderItemDAO;

    @Override
    @Transactional
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }

    @Override
    @Transactional
    public OrderItem getByOrderAndDish(Order order, Dish dish) {
        return orderItemDAO.getByOrderAndDish(order, dish);
    }
}
