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

    public void confirmOrder(String oid) throws OrderException {
        int state = orderDao.findStateByOid(oid);
        if(state != 3) {
            throw new OrderException("确认收货失败！");
        }
        orderDao.updateStateByOid(oid,4);
    }

    public void payOrder(String oid) throws OrderException {
        int state = orderDao.findStateByOid(oid);
        if(state != 1) {
            throw new OrderException("付款失败！");
        }
        orderDao.updateStateByOid(oid,2);
    }
}
