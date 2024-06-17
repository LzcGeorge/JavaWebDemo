package user.web.servlet;

import cn.itcast.commons.CommonUtils;
import user.domain.User;
import user.service.UserException;
import user.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // 依赖 UserService
        UserService userService = new UserService();
        HttpSession session = request.getSession();
        // 拿到数据
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        session.setAttribute("msg",""); // 初始化

        try {
            userService.login(form);
            // 登陆成功
            session.setAttribute("user",form);
            response.setHeader("Location","/user/welcome.jsp");
            response.setStatus(302);
        } catch (UserException e) {
            session.setAttribute("msg",e.getMessage());
            session.setAttribute("user",form);

            response.setHeader("Location","/user/index.jsp");
            response.setStatus(302);
        }
    }

}

