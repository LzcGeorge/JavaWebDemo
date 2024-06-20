package user.web.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet", value = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("method");

        if(methodName == null || methodName.trim().isEmpty()) {
            throw new RuntimeException("method 参数没传递");
        }
        Class clazz = this.getClass();

        Method method = null;
        try {
            method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(methodName + " 方法不存在");
        }

        try {
            String result = (String) method.invoke(this, req, resp);

            // 返回的字符串表示，重定向或者转发
            if(result == null || result.trim().isEmpty()) {
                return;
            }

            if(result.contains(":")) {
                int index = result.indexOf(":");
                String op = result.substring(0, index);
                String path = result.substring(index + 1);
                if(op.equalsIgnoreCase("forward")) {
                    req.getRequestDispatcher(path).forward(req,resp);
                } else if(op.equalsIgnoreCase("redirect")) {
                    resp.sendRedirect(req.getContextPath() + path);
                } else{
                    throw new RuntimeException(op + "操作暂时不支持");
                }
            } else {
                // 没有冒号，默认为转发
                req.getRequestDispatcher(result).forward(req,resp);
            }
        } catch (Exception e) {
            System.out.println(methodName + " 方法内部跑出了异常");
            throw new RuntimeException(e);
        }
    }

/**
 * below is test code
 */

//    public String addUser(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println("addUser");
//        return null;
//    }
//
//    public String edit(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println("edit");
//        throw new RuntimeException("just test");
//    }
//
//    public String fun1(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println("fun1");
//        return "forward:/index.jsp";
//    }
//
//
//    public String fun2(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println("fun2");
//
//        return "redirect:/index.jsp";
//    }
//    public String fun3(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println("fun3");
//
//        return "d:/index.jsp";
//    }

}
