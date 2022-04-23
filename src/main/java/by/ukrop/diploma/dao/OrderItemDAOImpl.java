package by.ukrop.diploma.dao;

import by.ukrop.diploma.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addOrderItem(OrderItem orderItem) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(orderItem);
    }
}
