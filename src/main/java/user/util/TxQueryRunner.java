package user.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.StatementConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 1、得到连接
 * 2、执行父类方法，传递连接对象
 * 3、释放连接
 * 4、返回值
 */
public class TxQueryRunner extends QueryRunner {

    @Override
    public int[] batch(String sql, Object[][] params) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        int[] res = super.batch(con,sql, params);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        T res = super.query(con,sql, rsh, params);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        T res = super.query(con,sql, rsh);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }

    @Override
    public int update(String sql) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        int res = super.update(con,sql);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }

    @Override
    public int update(String sql, Object param) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        int res = super.update(con,sql,param);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        Connection con = JdbcPoolUtils.getConnection();
        int res = super.update(con,sql,params);
        JdbcPoolUtils.releaseTransaction(con);
        return res;
    }
}
