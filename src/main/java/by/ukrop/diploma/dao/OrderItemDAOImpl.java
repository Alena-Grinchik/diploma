package by.ukrop.diploma.dao;

import by.ukrop.diploma.entity.Dish;
import by.ukrop.diploma.entity.Order;
import by.ukrop.diploma.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addOrderItem(OrderItem orderItem) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(orderItem);
    }

    @Override
    public OrderItem getByOrderAndDish(Order order, Dish dish) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        CriteriaQuery<OrderItem> cr = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = cr.from(OrderItem.class);

        Predicate sameOrder = cb.equal(root.get("order"), order);
        Predicate sameDish = cb.equal(root.get("dish"), dish);
        cr.select(root).where(cb.and(sameOrder, sameDish));
        Query<OrderItem> query = currentSession.createQuery(cr);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}