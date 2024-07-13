package bookstore.order.web.servlet;

import bookstore.order.domain.Order;
import bookstore.order.service.OrderException;
import bookstore.order.service.OrderService;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="AdminOrderServlet",urlPatterns = "/admin/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();
    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = orderService.findAll();
        req.setAttribute("orderList",orderList);
        return "/bookstore/adminjsps/admin/order/list.jsp";
    }

    public String findByState(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = Integer.parseInt(req.getParameter("state"));
        List<Order> orderList = orderService.findByState(state);
        req.setAttribute("orderList",orderList);
        return "/bookstore/adminjsps/admin/order/list.jsp";
    }

    public String dispatchOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("oid");
        try {
            orderService.dispatchOrder(oid);
        } catch (OrderException e) {
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/adminjsps/admin/msg.jsp";
        }
        List<Order> orderList = orderService.findByState(3);
        req.setAttribute("orderList",orderList);
        return "/bookstore/adminjsps/admin/order/list.jsp";
    }
}
