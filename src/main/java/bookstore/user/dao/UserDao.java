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



    public User findByCode(String code) {
        String sql = "select * from tb_user where code = ?";
        try {
            User user = qr.query(sql,new BeanHandler<User>(User.class),code);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(String uid,boolean state) {
        String sql = "update tb_user set state = ? where uid = ?";
        Object[] params = {state,uid};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("更新成功");
    }

    @Test
    public void test() {
//        System.out.println(findByUsername("a"));
//        System.out.println(findByEmail("123"));
//        User user = new User("123","sss","4","4","4",true);
//        addUser(user);
        updateState("A7E8D5C3C0B94D6E950025B4E1F3FCA1",true);
//        findByCode("D7B75C2D5CA64C11B9E827CB5AA99DAF7F1423CCF7A2474B9A4CBA3B5D86C83E");
    }
}
