package user.util.dbutils;

import user.util.JdbcPoolUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 简化 Demo 1 中的重复过程
 */
public class QR<T> {

    private DataSource dataSource;

    public QR() {
    }

    public QR(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 增，删，改操作
     * @param sql
     * @param params
     * @return
     */
    public int update(String sql,Object... params) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);

            initParams(pstmt,params);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) con.close();
            } catch (SQLException e1) {

            }

        }
    }

    // 给参数赋值
    private void initParams(PreparedStatement pstmt, Object... params) throws SQLException {
        for(int i = 0; i < params.length; i ++ ) {
            pstmt.setObject(i+1,params[i]);
        }
    }

    /**
     * 查询操作
     * @param sql
     * @param rh
     * @param params
     * @return
     */
    public T query(String sql,RsHandler<T> rh, Object... params) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);

            initParams(pstmt, params);

            rs = pstmt.executeQuery();

            return rh.handle(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e1) {

            }

        }
    }
}

/**
 * 把结果转化为 T 类型
 * @param <T>
 */
interface RsHandler<T> {
    public T handle(ResultSet rs) throws SQLException;
}