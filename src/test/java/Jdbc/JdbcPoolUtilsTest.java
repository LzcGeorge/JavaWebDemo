package Jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcPoolUtilsTest {
    @Test
    public void fun() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection con = dataSource.getConnection();
        System.out.println(con);
        con.close();
    }
}
