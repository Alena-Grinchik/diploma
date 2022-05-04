package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.User;

public interface UserService {
    Long addUser (User user);

    User getUser(Long id);

    void updateUser(User user);
}
