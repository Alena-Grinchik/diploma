package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl implements AddressDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addAddress(Address address) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(address);
    }

}
