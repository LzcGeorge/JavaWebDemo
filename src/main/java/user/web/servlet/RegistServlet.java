package user.web.servlet;
import cn.itcast.commons.CommonUtils;
import user.domain.User;
import user.service.UserException;
import user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // 依赖 UserService
        UserService userService = new UserService();

        // 拿到数据
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        HttpSession session = request.getSession();
        session.setAttribute("msg",""); // 初始化
        // 验证码
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if(!verifyCode.equalsIgnoreCase(form.getVerifyCode())) {
            session.setAttribute("msg","verifyCode wrongly,try again.");
            session.setAttribute("user",form);

            response.setHeader("Location","/user/regist.jsp");
            response.setStatus(302);
            return;
        }

        try {
            userService.regist(form);
            response.getWriter().println("<h1>注册成功!</h1> <a href = ' " +
                    request.getContextPath() + "/index.jsp" + "'>点击登陆</a>");
        } catch (UserException e) {
//            throw new RuntimeException(e);
//            response.setAttribute("msg",e.getMessage());
            System.out.println(e.getMessage());

//            request.setAttribute("msg",e.getMessage());
//            request.setAttribute("user",form);
//            // 转发
//            request.getRequestDispatcher("/regist.jsp").forward(request,response);

            // 使用 session 来转发，并改变url
            session.setAttribute("msg",e.getMessage());
            session.setAttribute("user",form);

            response.setHeader("Location","/user/regist.jsp");
            response.setStatus(302);
        }
    }

}

