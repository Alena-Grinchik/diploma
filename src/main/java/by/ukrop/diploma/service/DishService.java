package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.Dish;

public interface DishService {
    Dish getDish (Long id);

    void deleteDish(Long id);
}
