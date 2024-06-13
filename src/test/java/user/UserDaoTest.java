package user;

import org.junit.jupiter.api.Test;
import user.dao.UserDao;
import user.domain.User;

public class UserDaoTest {
    @Test
    public void testFindByUsername(){
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername("sefknow");
        System.out.println(user);
    }
    @Test
    public void testAdd() {
        UserDao userDao = new UserDao();
        User user = new User("lisi","456","aaaa");
        userDao.add(user);
    }
}
