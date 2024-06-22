package user.util.dbutils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.junit.Test;
import user.util.JdbcPoolUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class commons_dbutils {
    @Test
    public void add() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcPoolUtils.getDataSource());
        String sql = "insert into t_stu values(?,?,?,?)";
        Object[] params = {1002, "heihei", "88", "male"};
        qr.update(sql,params);
    }

    @Test
    public void query() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcPoolUtils.getDataSource());
        String sql = "select * from t_stu";
        Object[] params = {};
        System.out.println(qr.query(sql,new BeanListHandler<Stu>(Stu.class),params));
    }

    @Test
    public void mapQuery() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcPoolUtils.getDataSource());
        String sql = "select * from t_stu where sid = ?";
        Object[] params = {1};
        Map<String, Object> qs = qr.query(sql, new MapHandler(), params);
        // {sid=1, sname=ZhangSan, age=111, gender=male}
        System.out.println(qs);
    }
}
