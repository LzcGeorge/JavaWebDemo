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
            throw new OrderException("确认收货失败！当前状态为 " + state);
        }
        orderDao.updateStateByOid(oid,4);
    }

    public void payOrder(String oid) throws OrderException {
        int state = orderDao.findStateByOid(oid);
        if(state != 1) {
            throw new OrderException("付款失败！当前状态为 " + state);
        }
        orderDao.updateStateByOid(oid,2);
    }

    public List<Order> findAll() {
        // 需要多表查询，把每个订单的 orderitem 和 book都填充满
        return orderDao.findAll();
    }

    public List<Order> findByState(int state) {
        return orderDao.findByState(state);
    }

    public void dispatchOrder(String oid) throws OrderException {
        int state = orderDao.findStateByOid(oid);
        if(state != 2) {
            throw new OrderException("发货失败！当前状态为 " + state);
        }
        orderDao.updateStateByOid(oid,3);
    }
}
