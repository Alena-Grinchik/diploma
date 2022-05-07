package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Long addUser (User user);

    User getUser(Long id);

    void updateUser(User user);
}
