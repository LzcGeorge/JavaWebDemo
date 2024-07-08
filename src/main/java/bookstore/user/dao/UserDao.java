package bookstore.user.dao;

import bookstore.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;
import user.util.TxQueryRunner;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    public User findByUsername(String username) {
        try {
            String sql = "select * from tb_user where username = ?";
            return qr.query(sql,new BeanHandler<User>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        try {
            String sql = "select * from tb_user where email = ?";
            return qr.query(sql,new BeanHandler<User>(User.class),email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        try {
            String sql = "insert into tb_user values(?,?,?,?,?,?)";
            Object[] params = {user.getUid(),user.getUsername(),
                    user.getPassword(),user.getEmail(),user.getCode(),
                    user.isState()};
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() {
//        System.out.println(findByUsername("a"));
//        System.out.println(findByEmail("123"));
        User user = new User("123","sss","4","4","4",true);
//        addUser(user);
    }

}
