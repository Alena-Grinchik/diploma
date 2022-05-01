package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Order;

public interface OrderDAO {
    Order getOrder(Long id);

    Long addOrder(Order order);

    void updateOrder(Order order);
}
