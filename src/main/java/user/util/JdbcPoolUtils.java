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
    private static Connection con = null;

    public static Connection getConnection() throws SQLException {
        if(con == null) dataSource.getConnection();
        return con;
    }

    public static DataSource getDataSource() {
        return dataSource;
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
