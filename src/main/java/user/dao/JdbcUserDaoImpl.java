package user.dao;

import user.domain.User;
import user.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDaoImpl implements UserDao{

    @Override
    public User findByUsername(String username) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcUtils.getConnection();
            String sql = "select * from users where username = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if(rs == null) return null;

            if(rs.next()) {
                // 查到了
                // 转化成 User 对象，ORM：对象关系映射
                User _user = new User();
                _user.setUsername(rs.getString("username"));
                _user.setPassword(rs.getString("password"));
                return _user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) pstmt.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void add(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JdbcUtils.getConnection();
            String sql = "insert into users values(?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) pstmt.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
