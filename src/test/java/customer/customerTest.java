package customer;

import cn.itcast.commons.CommonUtils;
import customer.dao.CustomerDao;
import customer.domain.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

public class customerTest {
    @Test
    public void fun1() {
        CustomerDao customerDao = new CustomerDao();
        for(int i = 1; i <= 300; i ++) {
            Customer customer = new Customer();
            customer.setCid(CommonUtils.uuid());
            customer.setCname("cstm_" + i);
            customer.setBirthday("2024-06-24");
            customer.setGender(i%2==0?"男":"女");
            customer.setCellphone("1390634"+i);
            customer.setEmail("cstm" + i + "@gmail.com");
            customer.setDescription("new customer: " + i);
            customerDao.addCustomer(customer);
        }
    }



}
