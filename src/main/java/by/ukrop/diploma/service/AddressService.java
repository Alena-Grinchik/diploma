package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.Address;
import by.ukrop.diploma.persistence.entity.User;

public interface AddressService {
    Long addAddress(Address address);

    Address getLastUserAddress(User user);

    void updateAddress(Address address);
}
