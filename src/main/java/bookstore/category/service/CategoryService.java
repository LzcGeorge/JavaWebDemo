package bookstore.category.service;

import bookstore.category.dao.CategoryDao;
import bookstore.category.domain.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
