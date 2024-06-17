package user.service;

import user.dao.DaoFactory;
import user.dao.UserDao;
import user.domain.User;

public class UserService {
    private UserDao userDao = DaoFactory.getUserDao();
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

    public User login(User form) throws UserException {
        User _user = userDao.findByUsername(form.getUsername());
        if(_user == null) {
            // 抛出异常
            String msg = "username: " + form.getUsername() + " is not exist , please try again.";
            throw new UserException(msg);
        }

        if(!_user.getPassword().equals(form.getPassword())) {
            String msg = "username: " + form.getUsername() + "'s password wrongly , please try again.";

            throw new UserException(msg);
        }
        return _user;
    }

}
