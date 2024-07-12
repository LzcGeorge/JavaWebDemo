package bookstore.book.dao;

import bookstore.book.domain.Book;
import bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public Book findByBid(String bid) {
        String sql = "select * from book,category where book.cid = category.cid and book.bid = ?";

        try {
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            Book book = CommonUtils.toBean(map, Book.class);
            Category category = CommonUtils.toBean(map,Category.class);
            book.setCategory(category);
            System.out.println(book);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
