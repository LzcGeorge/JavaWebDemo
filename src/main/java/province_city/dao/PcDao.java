package province_city.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import province_city.domain.City;
import province_city.domain.Province;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.List;

public class PcDao {

    public List<Province> findAllProvince() {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "select * from t_province";
            return qr.query(sql,new BeanListHandler<Province>(Province.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> findByProvince(int pid) {
        try {
            QueryRunner qr = new TxQueryRunner();
            String sql = "select * from t_city where pid=?";
            return qr.query(sql,new BeanListHandler<City>(City.class),pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
