package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Order;
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

    @Override
    public void updateOrder(Order order) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(order);
    }
}
