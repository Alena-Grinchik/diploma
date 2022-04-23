package by.ukrop.diploma.dao;

import by.ukrop.diploma.entity.Order;

public interface OrderDAO {
    Order getOrder(Long id);

    Long addOrder(Order order);
}
