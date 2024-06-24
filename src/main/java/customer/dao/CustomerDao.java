package customer.dao;

import customer.domain.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import user.util.TxQueryRunner;

import java.sql.SQLException;
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
}
