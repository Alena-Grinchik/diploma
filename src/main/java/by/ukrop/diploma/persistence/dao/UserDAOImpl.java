package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(user);
    }

    @Override
    public User getUser(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(user);
    }
}
