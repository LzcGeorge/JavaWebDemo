package bookstore.book.dao;

import bookstore.book.domain.Book;
import bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.junit.Test;
import user.util.TxQueryRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Book> findAll() {
        String sql = "select * from book where del = false";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid = ? and del = false";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findByBid(String bid) {
        String sql = "select * from book,category where book.cid = category.cid and book.bid = ? and book.del = false";

        try {
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            Book book = CommonUtils.toBean(map, Book.class);
            Category category = CommonUtils.toBean(map,Category.class);
            book.setCategory(category);

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?,?)";
        Object[] params = {book.getBid(),book.getBname(),book.getPrice(),
                    book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.isDel()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(String bid) {
        String sql = "delete from book where bid = ?";
        try {
            qr.update(sql,bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void modifyBook(Book book) {
        String sql = "update book set bname = ? , price = ?, author = ?, cid = ? where bid = ?";
        Object[] params = {book.getBname(),book.getPrice(),
                book.getAuthor(),book.getCategory().getCid(),book.getBid()};
        System.out.println(book);
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
