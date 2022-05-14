package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Dish;
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

    @Override
    public void deleteDish(Long id) {
        Dish dish = getDish(id);
        dish.setCategory(null);

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(dish);
    }

    @Override
    public Long addDish(Dish dish) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(dish);
    }

    @Override
    public void updateDish(Dish dish) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(dish);
    }
}
