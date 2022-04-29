package by.ukrop.diploma.peristance.dao;

import by.ukrop.diploma.peristance.entity.Dish;
import by.ukrop.diploma.peristance.entity.Order;
import by.ukrop.diploma.peristance.entity.OrderItem;
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

    @Override
    public void removeOrderItem(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.clear();
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        currentSession.delete(orderItem);
    }

    @Override
    public OrderItem getOrderItem(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(OrderItem.class, id);
    }
}
