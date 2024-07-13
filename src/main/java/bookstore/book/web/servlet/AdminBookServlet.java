package bookstore.book.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminBookServlet",value = "/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    public String findAllBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> bookList = bookService.findAll();
        req.setAttribute("bookList",bookList);
        return "/bookstore/adminjsps/admin/book/list.jsp";
    }

    public String loadBookDesc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bid = req.getParameter("bid");
        Book bookDesc = bookService.findByBid(bid);

        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList",categoryList);
        req.setAttribute("bookDesc",bookDesc);
        return "/bookstore/adminjsps/admin/book/desc.jsp";
    }

    public String findAllCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList",categoryList);
        return "/bookstore/adminjsps/admin/book/add.jsp";
    }

    public String deleteBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bid = req.getParameter("bid");
        bookService.deleteBook(bid);
        List<Book> bookList = bookService.findAll();
        req.setAttribute("bookList",bookList);
        return "/bookstore/adminjsps/admin/book/list.jsp";
    }

    public String modifyBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bid = req.getParameter("bid");
        Book form = CommonUtils.toBean(req.getParameterMap(),Book.class);
        Category category = CommonUtils.toBean(req.getParameterMap(),Category.class);
        form.setCategory(category); // 只有 cid 没有 cname

        bookService.modifyBook(form);
        List<Book> bookList = bookService.findAll();
        req.setAttribute("bookList",bookList);
        return "/bookstore/adminjsps/admin/book/list.jsp";
    }
}
