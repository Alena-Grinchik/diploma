package by.ukrop.diploma.service;

import by.ukrop.diploma.peristance.entity.Order;

public interface OrderService {
    Order getOrder(Long id);

    Long addOrder(Order order);
}
