package user.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件：c3p0-config.xml
 *     包含 connection 的四大配置参数
 *     还有一些池配置参数
 */
public class JdbcPoolUtils {
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    /**
     * 当前线程、当前事务的专用连接
     *  它为null表示没有事务
     *  它不为null表示有事务
     *  当开启事务时，需要给它赋值
     *  当结束事务时，需要给它赋值为null
     *  并且在开启事务时，让dao的多个方法共享这个Connection
     */
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    public static Connection getConnection() throws SQLException {
        Connection con = tl.get();
        if(con == null) return dataSource.getConnection();
        return con;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void beginTransaction() throws SQLException {
        Connection con = tl.get();
        if(con != null) {
            throw new RuntimeException("已经开启了事务，勿重复开启");
        }
        con = dataSource.getConnection();
        con.setAutoCommit(false); // 开启事务
        tl.set(con);
    }

    public static void commitTransaction() throws SQLException {
        Connection con = tl.get();
        if(con == null) {
            throw new RuntimeException("还没有开启事务，不能提交");
        }
        con.commit();
        con.close();
        tl.remove();
    }

    public static void rollbackTransaction() throws SQLException {
        Connection con = tl.get();
        if(con == null) {
            throw new RuntimeException("还没有开启事务，不能回滚");
        }
        con.rollback();
        con.close();
        tl.remove();
    }

    /**
     * 释放连接
     *  判断是不是事务专用，如果是（后面还有事务），就不关闭。
     *  如果不是事务专用，那么就要关闭
     * @param connection
     */
    public static void releaseTransaction(Connection connection) throws SQLException {
        Connection con = tl.get();
        // con == null 说明现在没有事务，则关闭 connection
        if(con == null) connection.close();
        // con != connection 说明不是事务专用
        if(con != connection) connection.close();;
    }
}

/**
 * ThreadLocal 的实现原理。
 * @param <T>
 */
class TL<T> {
    private Map<Thread,T> map = new HashMap<Thread,T>();

    // 用当前线程做 key
    public void set(T data) {
        map.put(Thread.currentThread(),data);
    }

    public T get() {
        return map.get(Thread.currentThread());
    }

    public void remove() {
        map.remove(Thread.currentThread());
    }
}
