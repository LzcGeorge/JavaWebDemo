package bookstore.user.service;

import bookstore.user.dao.UserDao;
import bookstore.user.domain.User;

import java.sql.SQLException;

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

    public void active(String code) throws UserException {
        User user = userDao.findByCode(code);
        if(user == null) {
            throw new UserException("激活码无效");
        }

        if(user.isState() == true) {
            throw new UserException("您的账号已激活，无需重新激活");
        }


        userDao.updateState(user.getUid(),true);
        System.out.println("激活成功");
    }

    public User userLogin(User form) throws UserException {
        User user = userDao.findByUsername(form.getUsername());
        if(user == null) {
            throw new UserException("用户名不存在");
        }
        if(!user.getPassword().equals(form.getPassword())) {
            throw new UserException("用户名或密码错误");
        }
        // 是否激活
        if(!user.isState()) {
            throw new UserException("该账户未激活，请尽快去邮箱激活");
        }
        System.out.println("登录成功");
        return user;
    }
}
