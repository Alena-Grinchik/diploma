package by.ukrop.diploma.service;

import by.ukrop.diploma.peristance.dao.OrderDAO;
import by.ukrop.diploma.peristance.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDAO orderDAO;

    @Override
    @Transactional
    public Order getOrder(Long id) {
        return orderDAO.getOrder(id);
    }

    @Override
    @Transactional
    public Long addOrder(Order order) {
        return orderDAO.addOrder(order);
    }
}
