package bookstore.order.dao;

import bookstore.book.domain.Book;
import bookstore.order.domain.Order;
import bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
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
        String sql = "select * from orders where uid = ?";
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
}
