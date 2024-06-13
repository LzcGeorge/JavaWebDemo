package user.web.servlet;
import user.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // 依赖 UserService
        UserService userService = new UserService();
        System.out.println("access");
    }

}

