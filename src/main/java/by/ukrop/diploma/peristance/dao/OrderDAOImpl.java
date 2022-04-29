package by.ukrop.diploma.peristance.dao;

import by.ukrop.diploma.peristance.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl implements OrderDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Order getOrder(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Order.class, id);
    }

    @Override
    public Long addOrder(Order order) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(order);
    }
}
