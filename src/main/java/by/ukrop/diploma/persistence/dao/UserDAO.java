package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.User;

import java.util.List;

public interface UserDAO {
    Long addUser (User user);

    User getUser(Long id);

    void updateUser(User user);

    User getUserByEmail(String email);

    List<User> getAllUsers();
}
