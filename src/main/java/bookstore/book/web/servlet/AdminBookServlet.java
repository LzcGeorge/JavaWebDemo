package bookstore.book.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryService;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
}
