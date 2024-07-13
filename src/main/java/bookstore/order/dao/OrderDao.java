package bookstore.order.dao;

import bookstore.book.domain.Book;
import bookstore.order.domain.Order;
import bookstore.order.domain.OrderItem;
import bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr = new TxQueryRunner();

    public void addOrder(Order order) {
        String sql = "insert into orders values(?,?,?,?,?,?)";
        // util 的时间转化为 sql 中的timestamp
        Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
        Object[] params = {order.getOid(),timestamp,order.getTotal(),
                    order.getState(),order.getOwner().getUid(),order.getAddress()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrderItemList(List<OrderItem> orderItemList) {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Object[][] params = new Object[orderItemList.size()][];

        int i = 0;
        for(OrderItem orderItem: orderItemList) {
            params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(),orderItem.getSubtotal(),
                    orderItem.getOrder().getOid(), orderItem.getBook().getBid()};
            i ++;
        }
        try {
            qr.batch(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> loadOrdersByUid(String uid) {
        String sql = "select * from orders where uid = ? order by state,ordertime DESC";
        try {
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class), uid);

            for(Order order: orderList) {
                loadOrderItem(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderItem(Order order) {
        String sql = "select * from orderitem,book where orderitem.bid = book.bid and oid = ?";
        try {
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());

            // 利用 mapList 生成两个对象 book 和 orderItemList
            List<OrderItem> orderItemList = new ArrayList<OrderItem>();
            for(Map<String,Object> map: mapList) {
                OrderItem orderItem = CommonUtils.toBean(map,OrderItem.class);
                Book book = CommonUtils.toBean(map,Book.class);
                orderItem.setBook(book);
                orderItemList.add(orderItem);
            }
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order loadOrderByOid(String oid) {
        String sql = "select * from orders where oid = ?";

        try {
            Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);

            loadOrderItem(order);

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findStateByOid(String oid) {
        String sql = "select state from orders where oid = ?";
        try {
            return (int) qr.query(sql, new ScalarHandler<>(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStateByOid(String oid,int state) {
        String sql = "update orders set state = ? where oid = ?";
        try {
            qr.update(sql,state,oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findAll() {
        String sql = "select * from orders order by state,ordertime DESC";
        try {
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class));

            for(Order order: orderList) {
                loadOrderItem(order);
                loadOwner(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findByState(int state) {
        String sql = "select * from orders where state = ? order by uid,ordertime DESC";
        try {
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class),state);

            for(Order order: orderList) {
                loadOrderItem(order);
                loadOwner(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOwner(Order order) {
        String sql = "select * from orders,tb_user where orders.uid = tb_user.uid and oid = ?";
        try {
            Map<String, Object> map = qr.query(sql, new MapHandler(), order.getOid());
            User owner = CommonUtils.toBean(map,User.class);
            order.setOwner(owner);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
