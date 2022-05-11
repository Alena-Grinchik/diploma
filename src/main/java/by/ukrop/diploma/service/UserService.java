package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Long addUser (User user);

    User getUser(Long id);

    void updateUser(User user);

    List<User> getAllUsers();
}
