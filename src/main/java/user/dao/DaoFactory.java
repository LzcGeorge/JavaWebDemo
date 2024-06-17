package user.dao;

import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
    private static Properties props = null;
    static {
        // load dao.properties
        try {
            InputStream in = DaoFactory.class.
                    getClassLoader().getResourceAsStream("dao.properties");
            props = new Properties();
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static UserDao getUserDao() {
        String daoClassName = props.getProperty("user.dao.UserDao");

        // 通过反射拿到实现类，并实例化
        Class clazz = null;
        try {
            clazz = Class.forName(daoClassName);
            return (UserDao) clazz.newInstance();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
