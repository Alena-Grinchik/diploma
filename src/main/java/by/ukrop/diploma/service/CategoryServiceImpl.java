package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.CategoryDAO;
import by.ukrop.diploma.persistence.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    @Transactional
    public List<Category> getCategories() {
        return categoryDAO.getCategories();
    }

    @Override
    @Transactional
    public Category getCategory(Long id) {
        return categoryDAO.getCategory(id);
    }
}
