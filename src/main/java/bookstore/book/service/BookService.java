package bookstore.book.service;

import bookstore.book.dao.BookDao;
import bookstore.book.domain.Book;

import java.util.List;

public class BookService {
    private BookDao bookDao = new BookDao();

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    public Book findByBid(String bid) {
        return bookDao.findByBid(bid);
    }
}
