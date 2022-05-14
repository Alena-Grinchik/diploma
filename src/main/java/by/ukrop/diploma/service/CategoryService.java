package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.entity.Category;

import java.util.List;

public interface CategoryService {
   List<Category> getCategories();

   Category getCategory(Long id);
}
