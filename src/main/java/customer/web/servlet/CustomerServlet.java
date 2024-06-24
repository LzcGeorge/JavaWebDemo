package customer.web.servlet;

import cn.itcast.commons.CommonUtils;
import customer.domain.Customer;
import customer.service.CustomerService;
import user.domain.User;
import user.web.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CustomerServlet", value = "/CustomerServlet")
public class CustomerServlet extends BaseServlet {
    private CustomerService customerService = new CustomerService();

    public String addCustomer(HttpServletRequest req, HttpServletResponse resp){
        Customer form = CommonUtils.toBean(req.getParameterMap(), Customer.class);
        form.setCid(CommonUtils.uuid());
        customerService.addCustomer(form);
        req.setAttribute("msg", "添加 " + form.getCname() + " 顾客成功！");
        return "/customer/msg.jsp";
    }

    public String findAll(HttpServletRequest req, HttpServletResponse resp){
        List<Customer> all = customerService.findAll();
        req.setAttribute("customerList",all);
        return "/customer/list.jsp";
    }

    public String preEdit(HttpServletRequest req, HttpServletResponse resp){
        String cid = req.getParameter("cid");
        // 因为是在表单中出现的，所以一定能查到
        Customer customer = customerService.findByCid(cid);
        req.setAttribute("editCustomer",customer);
        return "/customer/edit.jsp";
    }

    public String edit(HttpServletRequest req, HttpServletResponse resp) {
        Customer form = CommonUtils.toBean(req.getParameterMap(), Customer.class);
        String cid = req.getParameter("cid");
        customerService.edit(cid,form);
        req.setAttribute("msg","修改 " + form.getCname() + " 顾客信息成功！");
        return "/customer/msg.jsp";
    }

    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        String cid = req.getParameter("cid");
        String cname = req.getParameter("cname");
        // 因为是在表单中出现的，所以一定能查到
        customerService.deleteByCid(cid);
        req.setAttribute("msg","删除 " + cname + " 顾客成功！");
        return "/customer/msg.jsp";
    }

    public String search(HttpServletRequest req, HttpServletResponse resp) {
        Customer form = CommonUtils.toBean(req.getParameterMap(), Customer.class);
        List<Customer> searchRes = customerService.search(form);
        System.out.println(searchRes);
        req.setAttribute("customerList",searchRes);
        return "/customer/list.jsp";
    }
}
