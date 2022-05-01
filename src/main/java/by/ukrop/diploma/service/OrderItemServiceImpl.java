package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.OrderItemDAO;
import by.ukrop.diploma.persistence.entity.Dish;
import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

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

    @Override
    @Transactional
    public void removeOrderItem(Long id) {
        orderItemDAO.removeOrderItem(id);
    }

    @Override
    @Transactional
    public void updateOrderItemQuantity(Long id, Long delta) {
        OrderItem orderItem = orderItemDAO.getOrderItem(id);
        orderItem.setQuantity(orderItem.getQuantity() + delta);
        if (orderItem.getQuantity() <= 0) {
            orderItemDAO.removeOrderItem(id);
        } else {
            orderItemDAO.addOrderItem(orderItem);
        }
    }
}
