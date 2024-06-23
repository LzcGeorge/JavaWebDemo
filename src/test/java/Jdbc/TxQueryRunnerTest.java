package Jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;
import user.util.JdbcPoolUtils;
import user.util.TxQueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class TxQueryRunnerTest {
    public static void daoUpdate(String name,double money) throws SQLException {
        QueryRunner qr = new TxQueryRunner();
        String sql = "update account set balance = balance + ? where name = ?";
        Object[] params = {money,name};
        qr.update(sql,params);
    }
    @Test
    public void serviceQR() throws SQLException {
        try {
            JdbcPoolUtils.beginTransaction();
            daoUpdate("zs",-100);
//            if(true) throw new RuntimeException("test"); // 发生回滚
            daoUpdate("ls",100);
            JdbcPoolUtils.commitTransaction();
        } catch (SQLException e) {
            try {
                JdbcPoolUtils.rollbackTransaction();
            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
            }
            throw e;
        }
    }
}
