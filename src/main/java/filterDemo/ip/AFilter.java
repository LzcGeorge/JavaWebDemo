package filterDemo.ip;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/filterDemo/*")
public class AFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ServletContext application = request.getServletContext();
        Map<String,Integer> map = (Map<String, Integer>) application.getAttribute("map");

        // 获取 ip
        String ip = request.getRemoteAddr();
        int cnt = map.getOrDefault(ip,0);
        map.put(ip,cnt + 1);

        chain.doFilter(request,response);
    }
}
