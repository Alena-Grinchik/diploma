package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Category;
import by.ukrop.diploma.persistence.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> getCategories() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery< Category > cq = cb.createQuery(Category.class);
        Root< Category > root = cq.from(Category.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Category getCategory(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Category.class, id);
    }
}
