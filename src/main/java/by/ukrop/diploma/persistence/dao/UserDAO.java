package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.User;

public interface UserDAO {
    Long addUser (User user);

    User getUser(Long id);

    void updateUser(User user);
}
