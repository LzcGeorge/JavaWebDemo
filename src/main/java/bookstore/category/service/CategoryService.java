package bookstore.category.service;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.dao.CategoryDao;
import bookstore.category.domain.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    private BookService bookService = new BookService();
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public void addCategory(Category form) throws CategoryException {
        Category category = categoryDao.findCategoryByCname(form.getCname());
        if(category != null) {
            throw new CategoryException("分类名称已存在，请重新输入!");
        }
        categoryDao.addCategory(form);
    }

    public void deleteCategory(String cid) throws CategoryException {
        List<Book> byCategory = bookService.findByCategory(cid);
        if(!byCategory.isEmpty()) {
            throw new CategoryException("该分类下还有图书，无法直接删除");
        }
        categoryDao.deleteCategory(cid);
    }

    public Category findCategoryByCid(String cid) {
        return categoryDao.findCategoryByCid(cid);
    }

    public void modifyCategoryCname(String cid,String newCname) throws CategoryException {
        Category categoryByCname = categoryDao.findCategoryByCname(newCname);
        if(categoryByCname != null) {
            throw new CategoryException("分类名称已存在，请重新输入!");
        }
        categoryDao.modifyCategoryCname(cid,newCname);
    }
}
