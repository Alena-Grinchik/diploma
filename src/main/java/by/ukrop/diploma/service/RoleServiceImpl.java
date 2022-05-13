package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.RoleDAO;
import by.ukrop.diploma.persistence.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional
    public Role getRole(Long id) {
        return roleDAO.getRole(id);
    }
}
