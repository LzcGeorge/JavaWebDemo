package filterDemo.ip;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@WebListener
public class AListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ServletContextListener.super.contextInitialized(sce);
        Map<String,Integer> map = new HashMap<>();
        ServletContext application = sce.getServletContext();
        application.setAttribute("map",map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ServletContextListener.super.contextDestroyed(sce);

    }
}
