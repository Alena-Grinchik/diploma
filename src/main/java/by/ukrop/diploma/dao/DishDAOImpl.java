package by.ukrop.diploma.dao;

import by.ukrop.diploma.entity.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DishDAOImpl implements DishDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Dish getDish(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Dish.class, id);
    }
}
