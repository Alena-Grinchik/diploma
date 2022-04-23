package by.ukrop.diploma.service;

import by.ukrop.diploma.dao.DishDAO;
import by.ukrop.diploma.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishDAO dishDAO;

    @Override
    @Transactional
    public Dish getDish(Long id) {
        return dishDAO.getDish(id);
    }
}
