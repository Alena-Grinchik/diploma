package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.UserDAO;
import by.ukrop.diploma.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public Long addUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    @Transactional
    public User getUser(Long id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User userByEmail = userDAO.getUserByEmail(username);
       if (userByEmail==null) {
           throw new UsernameNotFoundException("Пользователь не зарегистрирован");
       } else {
           return userByEmail;
       }
    }
}
