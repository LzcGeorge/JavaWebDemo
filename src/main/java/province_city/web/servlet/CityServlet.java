package province_city.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;
import province_city.dao.PcDao;
import province_city.domain.City;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CityServlet",value = "/CityServlet")
public class CityServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        int pid = Integer.parseInt(req.getParameter("pid"));
        Gson gson = new Gson();
        PcDao pcDao = new PcDao();
        List<City> byProvince = pcDao.findByProvince(pid);
        System.out.println(gson.toJson(byProvince));
        resp.getWriter().print(gson.toJson(byProvince));
    }
//    @Test
//    public void test() {
//        PcDao pcDao = new PcDao();
//        List<City> byProvince = pcDao.findByProvince(1);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(byProvince));
//    }
}
