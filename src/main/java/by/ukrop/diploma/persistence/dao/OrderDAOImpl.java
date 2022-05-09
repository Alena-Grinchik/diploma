package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Order;
import by.ukrop.diploma.persistence.entity.User;
import by.ukrop.diploma.service.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Override
    public Order getLastUserCart(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);

        Predicate sameUser = cb.equal(root.get("user"), user);
        Predicate sameStatus = cb.equal(root.get("status"), OrderStatus.DRAFT);
        cr.select(root).where(cb.and(sameUser, sameStatus)).orderBy(cb.desc(root.get("id")));
        Query<Order> query = currentSession.createQuery(cr);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Order> ordersInProgress() {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);

        Predicate sameStatusSubmitted = cb.equal(root.get("status"), OrderStatus.SUBMITTED);
        Predicate sameStatusApproved = cb.equal(root.get("status"), OrderStatus.APPROVED);
        cr.select(root).where(cb.or(sameStatusSubmitted, sameStatusApproved)).orderBy(cb.desc(root.get("id")));
        Query<Order> query = currentSession.createQuery(cr);
        return query.getResultList();
    }
}
