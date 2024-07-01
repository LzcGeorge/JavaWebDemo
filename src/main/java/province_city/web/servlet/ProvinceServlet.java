package province_city.web.servlet;

import com.google.gson.Gson;
import org.junit.Test;
import province_city.dao.PcDao;
import province_city.domain.Province;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProvinceServlet",value = "/ProvinceServlet")
public class ProvinceServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PcDao pcDao = new PcDao();
        List<Province> allProvince = pcDao.findAllProvince();
        Gson gson = new Gson();
        resp.getWriter().print(gson.toJson(allProvince));
    }

//    @Test
//    public void test() {
//        PcDao pcDao = new PcDao();
//        List<Province> allProvince = pcDao.findAllProvince();
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(allProvince));
//    }
}
