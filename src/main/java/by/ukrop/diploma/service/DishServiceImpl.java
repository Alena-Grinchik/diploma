package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.DishDAO;
import by.ukrop.diploma.persistence.entity.Dish;
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
        Dish dish = dishDAO.getDish(id);
        if (dish != null && dish.getCategory() == null) {
            return null;
        }
        return dish;
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        dishDAO.deleteDish(id);
    }

    @Override
    @Transactional
    public Long addDish(Dish dish) {
        return dishDAO.addDish(dish);
    }

    @Override
    @Transactional
    public void updateDish(Dish dish) {
        dishDAO.updateDish(dish);
    }

}
