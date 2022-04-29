package by.ukrop.diploma.peristance.dao;

import by.ukrop.diploma.peristance.entity.Order;

public interface OrderDAO {
    Order getOrder(Long id);

    Long addOrder(Order order);
}
