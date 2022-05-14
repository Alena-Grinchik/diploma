package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Discount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountDAOImpl implements DiscountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Discount getDiscount(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Discount.class, id);
    }
}
