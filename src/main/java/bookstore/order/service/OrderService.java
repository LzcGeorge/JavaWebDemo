package bookstore.order.service;

import bookstore.order.dao.OrderDao;
import bookstore.order.domain.Order;
import user.util.JdbcPoolUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public void addOrder(Order order) {
        try {
            JdbcPoolUtils.beginTransaction();
            orderDao.addOrder(order);
            orderDao.addOrderItemList(order.getOrderItemList());
            JdbcPoolUtils.commitTransaction();
        } catch (Exception e) {
            try {
                JdbcPoolUtils.rollbackTransaction();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<Order> loadOrdersByUid(String uid) {
        return orderDao.loadOrdersByUid(uid);
    }

    public Order loadOrderByOid(String oid) {
        return orderDao.loadOrderByOid(oid);
    }
}
