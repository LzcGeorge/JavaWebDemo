package bookstore.user.web.servlet;

import bookstore.user.domain.User;
import bookstore.user.service.UserException;
import bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import user.web.servlet.BaseServlet;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Session;
@WebServlet(name = "UserServlet",urlPatterns = "/api/UserServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    public String active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        try {
            userService.active(code);
        } catch (UserException e) {
//            throw new RuntimeException(e);
            req.setAttribute("msg",e.getMessage());
            return "/bookstore/jsps/msg.jsp";
        }
        System.out.println("激活成功");
        req.setAttribute("msg","激活成功，请登录");
        return "/bookstore/jsps/msg.jsp";
    }

    public String userRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User form = CommonUtils.toBean(req.getParameterMap(),User.class);

        // 补全 uid 和 激活码
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        /**
         * 输入校验
         */
        Map<String,String> errors = new HashMap<String,String>();
        String username = form.getUsername();
        if(username == null || username.trim().isEmpty()) {
            errors.put("username","用户名不能为空");
        } else if(username.length() < 3 || username.length() > 10){
            errors.put("username","用户名长度必须在3～10之间");
        }

        String password = form.getPassword();
        if(password == null || password.trim().isEmpty()) {
            errors.put("password","用户名不能为空");
        } else if(username.length() < 3 ){
            errors.put("password","password长度必须大于2");
        }
        String email = form.getEmail();
        if(email == null || email.trim().isEmpty()) {
            errors.put("email","邮箱不能为空");
        } else if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            errors.put("email","Email格式错误");
        }

        // 输入校验错误，返回错误信息
        if(errors.size() > 0) {
            req.setAttribute("errors",errors);
            req.setAttribute("form",form);
            return "/bookstore/jsps/user/regist.jsp";
        }

        /**
         * 调用 service 的方法
         */
        try {
            userService.userRegist(form);
        } catch (UserException e) {
            req.setAttribute("msg",e.getMessage());
            req.setAttribute("form",form);
            return "/bookstore/jsps/user/regist.jsp";
        }

        /**
         * 发邮件
         */
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        String host= props.getProperty("host");
        String uname = props.getProperty("uname");
        String pwd = props.getProperty("pwd");
        String from = props.getProperty("from");
        String to = form.getEmail();
        String subject = props.getProperty("subject");
        String content = props.getProperty("content");

        content = MessageFormat.format(content,form.getCode()); // 替换 {0}
        Session session = MailUtils.createSession(host,uname,pwd);
        Mail mail = new Mail(from,to,subject,content);
        try {
            System.out.println("发邮件了");
            MailUtils.send(session,mail);
        } catch (MessagingException e) {
            System.out.println("邮件发送失败");
            throw new RuntimeException(e);
        }



        // 注册成功
        req.setAttribute("msg",form.getUsername() + " 注册成功！请马上到邮箱激活");
        return "/bookstore/jsps/msg.jsp";
    }

    public String userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User form = CommonUtils.toBean(req.getParameterMap(), User.class);
        try {
            User user = userService.userLogin(form);

            // 因为需要 redirect 所以把用户信息存到 session 中
            req.getSession().setAttribute("session_user",user);
            return "redirect:/bookstore/jsps/main.jsp";
        } catch (UserException e) {
            req.setAttribute("msg",e.getMessage());
            req.setAttribute("form",form);
            return "/bookstore/jsps/user/login.jsp";
        }
    }
    public String userQuit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        return "redirect:/bookstore/jsps/main.jsp";

    }
}
