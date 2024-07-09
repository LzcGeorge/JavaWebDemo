package bookstore.category.web.servlet;

import bookstore.category.service.CategoryService;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CategoryServlet",urlPatterns = "/api/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categoryList",categoryService.findAll());
        return "/bookstore/jsps/left.jsp";
    }
}
