package customer.service;

import customer.dao.CustomerDao;
import customer.domain.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();

    public void addCustomer(Customer form) {
        customerDao.addCustomer(form);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public Customer findByCid(String cid) {
        return customerDao.findByCid(cid);
    }

    public void edit(String cid, Customer form) {
        customerDao.edit(cid,form);
    }

    public void deleteByCid(String cid) {
        customerDao.deleteByCid(cid);
    }

    public List<Customer> search(Customer form) {
        return customerDao.search(form);
    }
}
