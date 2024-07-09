package bookstore.book.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet",urlPatterns = "/api/BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> bookList = bookService.findAll();
        req.setAttribute("bookList",bookList);
        return "/bookstore/jsps/book/list.jsp";
    }

    public String findByCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        List<Book> bookList = bookService.findByCategory(cid);
        req.setAttribute("bookList",bookList);
        return "/bookstore/jsps/book/list.jsp";
    }

    public String loadBookDescription(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bid = req.getParameter("bid");
        Book book = bookService.findByBid(bid);
        req.setAttribute("bookDesc",book);
        return "/bookstore/jsps/book/desc.jsp";
    }

}
