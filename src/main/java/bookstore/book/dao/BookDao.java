package bookstore.book.dao;

import bookstore.book.domain.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.List;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid = ?";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
