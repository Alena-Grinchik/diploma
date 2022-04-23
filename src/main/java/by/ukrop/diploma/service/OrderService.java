package by.ukrop.diploma.service;

import by.ukrop.diploma.entity.Order;

public interface OrderService {
    Order getOrder(Long id);

    Long addOrder(Order order);
}
