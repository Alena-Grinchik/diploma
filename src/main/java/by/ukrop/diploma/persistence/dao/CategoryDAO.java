package by.ukrop.diploma.persistence.dao;

import by.ukrop.diploma.persistence.entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> getCategories();

    Category getCategory(Long id);
}
