package bookstore.category.dao;

import bookstore.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findCategoryByCname(String cname) {
        String sql = "select * from category where cname = ?";
        try {
            return qr.query(sql,new BeanHandler<>(Category.class),cname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCategory(Category form) {
        String sql = "insert into category values(?,?)";
        try {
            qr.update(sql,form.getCid(),form.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory(String cid) {
        String sql = "delete from category where cid = ?";
        try {
            qr.update(sql,cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findCategoryByCid(String cid) {
        String sql = "select * from category where cid = ?";
        try {
            return qr.query(sql,new BeanHandler<>(Category.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyCategoryCname(String cid,String newCname) {
        String sql = "update category set cname = ? where cid = ?";
        try {
            qr.update(sql, newCname, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
