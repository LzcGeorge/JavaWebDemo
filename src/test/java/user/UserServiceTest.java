package user;

import org.junit.jupiter.api.Test;
import user.domain.User;
import user.service.UserException;
import user.service.UserService;

public class UserServiceTest {


    @Test
    public void query() {
        UserService userService = new UserService();
        User user = new User("exi","1111");
        try {
            userService.regist(user);
        } catch (UserException e) {
//            throw new RuntimeException(e);
//            System.out.println(e.getMsg());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
