package bookstore.category.dao;

import bookstore.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            List<Category> query = qr.query(sql, new BeanListHandler<Category>(Category.class));
            System.out.println(query);
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
