package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Dish;

public interface DishDAO {
    Dish getDish (Long id);

    void deleteDish(Long id);

    Long addDish(Dish dish);

    void updateDish(Dish dish);
}
