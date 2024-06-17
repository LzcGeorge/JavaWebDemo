package user.dao;

import user.domain.User;

public interface UserDao {
    public User findByUsername(String username);
    public void add(User user);
}
