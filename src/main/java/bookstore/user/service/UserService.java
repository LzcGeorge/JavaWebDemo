package bookstore.user.service;

import bookstore.user.dao.UserDao;
import bookstore.user.domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public void userRegist(User form) throws UserException {
        User user = userDao.findByUsername(form.getUsername());
        if(user != null) {
            throw new UserException("用户名已被注册");
        }
        user = userDao.findByEmail(form.getEmail());
        if(user != null) {
            throw new UserException("Email已被注册");
        }

        userDao.addUser(form);
    }
}
