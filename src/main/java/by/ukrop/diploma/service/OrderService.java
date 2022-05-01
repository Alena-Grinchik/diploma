package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.Order;

public interface OrderService {
    Order getOrder(Long id);

    Long addOrder(Order order);
}
