package bookstore.order.web.servlet;

import bookstore.cart.domain.Cart;
import bookstore.cart.domain.CartItem;
import bookstore.order.domain.Order;
import bookstore.order.domain.OrderItem;
import bookstore.order.service.OrderException;
import bookstore.order.service.OrderService;
import bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import user.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet",urlPatterns = "/api/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();


    public String addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart)req.getSession().getAttribute("cart");

        // 利用 cart 生成订单 order
        Order order = new Order();
        order.setOid(CommonUtils.uuid());
        order.setOrdertime(new Date());
        order.setState(1);
        User user = (User) req.getSession().getAttribute("session_user");
        order.setOwner(user);
        order.setTotal(cart.getCartTotal());

        // 利用 cartItem 生成 orderItem
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(CartItem cartItem: cart.getCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setIid(CommonUtils.uuid());
            oi.setCount(cartItem.getCount());
            oi.setBook(cartItem.getBook());
            oi.setSubtotal(cartItem.getSubtotal());
            oi.setOrder(order);

            orderItemList.add(oi);
        }

        order.setOrderItemList(orderItemList);
        cart.clearCartItem();
        orderService.addOrder(order);
        req.setAttribute("order",order);
        return "/bookstore/jsps/order/desc.jsp";
    }

    public String loadMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        List<Order> orderList = orderService.loadOrdersByUid(uid);
        req.setAttribute("orderList",orderList);
        return "/bookstore/jsps/order/list.jsp";
    }

    public String loadOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("oid");
        Order order = orderService.loadOrderByOid(oid);
        req.setAttribute("order",order);
        return "/bookstore/jsps/order/desc.jsp";
    }

    public String confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("oid");

        try {
            orderService.confirmOrder(oid);
        } catch (OrderException e) {
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/jsps/msg.jsp";
        }
        req.setAttribute("msg","订单确认收货成功！");
        return "/bookstore/jsps/msg.jsp";
    }

    public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("oid");
        try {
            orderService.payOrder(oid);
        } catch (OrderException e) {
            req.setAttribute("msg","订单付款失败！！！");
            return "/bookstore/jsps/msg.jsp";
        }
        req.setAttribute("msg","订单付款成功！");
        return "/bookstore/jsps/msg.jsp";
    }
}
