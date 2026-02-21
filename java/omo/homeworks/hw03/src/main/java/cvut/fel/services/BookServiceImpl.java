package cvut.fel.services;

import cvut.fel.dao.BookRepository;
import cvut.fel.model.Book;
import cvut.fel.services.strategy.BookStrategy;

public class BookServiceImpl {
    BookRepository bookRepository = new BookRepository();
    private BookStrategy strategy;

    public void setBookStrategy(BookStrategy bookStrategy) {
        this.strategy = bookStrategy;
    }

    public Book getByBookId(int bookId) {
        //more logic for books in here
        return bookRepository.getById(bookId);
    }

    public Book getByBookName(String name) {
        //more logic for books in here
        return bookRepository.getByName(name);
    }

    public Book updateBook(Book book) {
        if (strategy != null) {
            return strategy.update(book);
        }
        throw new IllegalStateException("Strategy is not set");
    }

}
