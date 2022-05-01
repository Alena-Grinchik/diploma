package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.AddressDAO;
import by.ukrop.diploma.persistence.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDAO addressDAO;

    @Override
    @Transactional
    public Long addAddress(Address address) {
        return addressDAO.addAddress(address);
    }
}
