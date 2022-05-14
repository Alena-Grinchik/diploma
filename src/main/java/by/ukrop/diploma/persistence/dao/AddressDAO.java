package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Address;
import by.ukrop.diploma.persistence.entity.User;

public interface AddressDAO {
    Long addAddress(Address address);

    Address getLastUserAddress(User user);

    void updateAddress(Address address);
}
