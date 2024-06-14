package user.service;

import user.dao.UserDao;
import user.domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public void regist(User user) throws UserException {
        User _user = userDao.findByUsername(user.getUsername());
        if(_user != null) {
            // 抛出异常
            String msg = "username: " + user.getUsername() + " has exist , please try again.";
            throw new UserException(msg);
        }

        // 添加用户
        userDao.add(user);

    }

}
