package bookstore.category.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryException;
import bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet",urlPatterns = "/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categoryList",categoryService.findAll());
        return "/bookstore/adminjsps/admin/category/list.jsp";
    }

    public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cname = req.getParameter("cname");
        String cid = CommonUtils.uuid();
        Category category = new Category(cid,cname);
        try {
            categoryService.addCategory(category);
        } catch (CategoryException e) {
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/adminjsps/admin/category/add.jsp";
        }
        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList",categoryList);
        return "/bookstore/adminjsps/admin/category/list.jsp";
    }

    public String toEditCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        Category category = categoryService.findCategoryByCid(cid);

        req.setAttribute("editCategory",category);
        return "/bookstore/adminjsps/admin/category/mod.jsp";
    }
    public String modifyCategoryCname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        String newCname = req.getParameter("newCname");
        try {
            categoryService.modifyCategoryCname(cid,newCname);
        } catch (CategoryException e) {
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/adminjsps/msg.jsp";
        }

        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList",categoryList);
        return "/bookstore/adminjsps/admin/category/list.jsp";
    }

    public String deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String cid = req.getParameter("cid");

        try {
            categoryService.deleteCategory(cid);
        } catch (CategoryException e) {
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/adminjsps/msg.jsp";
        }
        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList",categoryList);
        return "/bookstore/adminjsps/admin/category/list.jsp";
    }


}
