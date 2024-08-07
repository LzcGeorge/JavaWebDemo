package bookstore.cart.domain;

import bookstore.book.domain.Book;

import java.math.BigDecimal;

public class CartItem {
    private Book book;
    private int count;

    public CartItem() {
    }

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }

    public double getSubtotal() {
        BigDecimal price = new BigDecimal("" + book.getPrice());
        BigDecimal cnt = new BigDecimal("" + count);

        return price.multiply(cnt).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
