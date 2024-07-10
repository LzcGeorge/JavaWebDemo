package bookstore.cart.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.cart.domain.Cart;
import bookstore.cart.domain.CartItem;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet",urlPatterns = "/api/CartServlet")
public class CartServlet extends BaseServlet {
    public String addCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到购物车
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        if(cart == null) {
            req.setAttribute("msg","未登录，请先登录！");
            return "/bookstore/jsps/body.jsp";
        }
        // 拿到参数
        String bid = req.getParameter("bid");
        int count = Integer.parseInt(req.getParameter("count"));
        Book book = new BookService().findByBid(bid);
        CartItem cartItem = new CartItem(book,count);
        cart.addCartItem(cartItem);
        return "/bookstore/jsps/cart/list.jsp";
    }

    public String deleteCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到购物车
        Cart cart = (Cart)req.getSession().getAttribute("cart");

        // 拿到参数
        String bid = req.getParameter("bid");

        cart.deleteCartItem(bid);
        return "/bookstore/jsps/cart/list.jsp";
    }

    public String clearCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到购物车
        Cart cart = (Cart)req.getSession().getAttribute("cart");

        cart.clearCartItem();
        return "/bookstore/jsps/cart/list.jsp";
    }
}
