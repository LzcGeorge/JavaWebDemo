package user.util.dbutils;

import user.util.JdbcPoolUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * commons-dbutils
 *  简化jdbc的代码
 */
public class Demo1 {
    public static void addStu(Stu stu) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcPoolUtils.getConnection();
            System.out.println(con);
            String sql = "insert into t_stu values(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,stu.getSid());
            pstmt.setString(2,stu.getSname());
            pstmt.setInt(3,stu.getAge());
            pstmt.setString(4, stu.getGender());

            System.out.println(pstmt.executeUpdate());
        } catch (Exception e) {

        } finally {

        }
    }

    public static void updateStu(Stu stu) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcPoolUtils.getConnection();
            String sql = "update t_stu set sname=?,age=?,gender=? where sid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(4,stu.getSid());
            pstmt.setString(1,stu.getSname());
            pstmt.setInt(2,stu.getAge());
            pstmt.setString(3, stu.getGender());

            pstmt.executeUpdate();
        } catch (Exception e) {

        } finally {

        }
    }
    public static void deleteStu(int sid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcPoolUtils.getConnection();
            String sql = "delete from t_stu where sid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,sid);
            pstmt.executeUpdate();
        } catch (Exception e) {

        } finally {

        }
    }

    public Stu query(int sid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JdbcPoolUtils.getConnection();
            String sql = "select * from t_stu where sid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,sid);

            rs = pstmt.executeQuery();
            if(!rs.next()) return null;

            /**
             * rs 转化为 Stu 对象
             *  用反射完成 toBean
             */

        } catch (Exception e) {

        } finally {

        }
        return null;
    }

    public List<Stu> queryAll() {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcPoolUtils.getConnection();
            String sql = "select * from t_stu";
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {

        } finally {

        }
        return null;
    }


}
