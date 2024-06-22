package user.util.dbutils;

import org.junit.Test;
import user.util.JdbcPoolUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QRTest {
    @Test
    public void fun() {
        Stu stu = new Stu(22,"LiSi",111,"female");
//        addUser(stu);
        System.out.println(loadAll());
    }
    public void addUser(Stu stu) {
        QR qr = new QR(JdbcPoolUtils.getDataSource());
        String sql = "insert into t_stu values(?,?,?,?)";
        Object[] params = {stu.getSid(),stu.getSname(),stu.getAge(),stu.getGender()};
        qr.update(sql,params);
    }

    public Stu load(int sid) {
        QR qr = new QR(JdbcPoolUtils.getDataSource());
        String sql = "select * from t_stu where sid=?";
        Object[] params = {sid};

        RsHandler<Stu> rh = new RsHandler<Stu>() {
            @Override
            public Stu handle(ResultSet rs) throws SQLException {
                if(!rs.next()) return null;
                Stu stu = new Stu();
                stu.setSid(rs.getInt("sid"));
                stu.setSname(rs.getString("sname"));
                stu.setAge(rs.getInt("age"));
                stu.setGender(rs.getString("gender"));
                return stu;
            }
        };

        return (Stu) qr.query(sql,rh,params);
    }

    public List<Stu> loadAll() {
        QR qr = new QR(JdbcPoolUtils.getDataSource());
        String sql = "select * from t_stu";
        Object[] params = {};

        RsHandler<List<Stu>> rh = new RsHandler<List<Stu>>() {
            @Override
            public List<Stu> handle(ResultSet rs) throws SQLException {
                List<Stu> stus = new ArrayList<Stu>();
                while(rs.next()) {
                    Stu stu = new Stu();
                    stu.setSid(rs.getInt("sid"));
                    stu.setSname(rs.getString("sname"));
                    stu.setAge(rs.getInt("age"));
                    stu.setGender(rs.getString("gender"));
                    stus.add(stu);
                }

                return stus;
            }
        };

        return (List<Stu>) qr.query(sql,rh,params);
    }
}
