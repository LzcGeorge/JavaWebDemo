package customer.dao;

import customer.domain.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDao {

    public void addCustomer(Customer form) {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "insert into t_customer values(?,?,?,?,?,?,?)";
            Object[] params = {form.getCid(), form.getCname(), form.getGender(), form.getBirthday(),
                    form.getCellphone(), form.getEmail(), form.getDescription()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> findAll() {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "select * from t_customer";
            return qr.query(sql,new BeanListHandler<Customer>(Customer.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer findByCid(String cid) {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "select * from t_customer where cid=?";
            Object[] params = {cid};
            return qr.query(sql,new BeanHandler<Customer>(Customer.class),params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(String cid, Customer form) {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "update t_customer set cname=?,gender=?,birthday=?,cellphone=?,email=?,description=?" +
                    "where cid = ?";
            Object[] params = {form.getCname(), form.getGender(), form.getBirthday(),
                    form.getCellphone(), form.getEmail(), form.getDescription(),cid};
           qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByCid(String cid) {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "delete from t_customer where cid=?";
            qr.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> search(Customer form) {
        try {
            QueryRunner qr = new TxQueryRunner();
            List<Object> params = new ArrayList<>();
            StringBuilder sql = new StringBuilder("select * from t_customer where 1=1");
            String cname = form.getCname();
            System.out.println(form);
            if(cname != null && !cname.trim().isEmpty()) {
                sql.append(" and cname like ?");
                params.add("%" + cname + "%");
            }

            String gender = form.getGender();
            if(gender != null && !gender.trim().isEmpty()) {
                sql.append(" and gender = ?");
                params.add(gender);
            }
            String cellphone = form.getCellphone();
            if(cellphone != null && !cellphone.trim().isEmpty()) {
                sql.append(" and cellphone like ?");
                params.add("%" + cellphone + "%");
            }
            String email = form.getEmail();
            if(email != null && !email.trim().isEmpty()) {
                sql.append(" and email like ?");
                params.add("%" + email + "%");
            }

            List<Customer> customers = qr.query(sql.toString(), new BeanListHandler<Customer>(Customer.class), params.toArray());
            return customers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fun2() {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "select * from t_customer where 1=1 and cname like ?";
            List<Object> list = new ArrayList<>();
            String cname = "23";
            list.add("%" + cname + "%");
            System.out.println(list.toString());
            List<Customer> customers = qr.query(sql, new BeanListHandler<Customer>(Customer.class),list.toArray());
            System.out.println(customers.toString());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
