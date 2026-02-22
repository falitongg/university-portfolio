package cvut.fel.services.strategy;

import cvut.fel.dao.BookRepository;
import cvut.fel.model.Book;

public class SimpleBookStrategy implements BookStrategy {
    //TODO Bude potřeba implementovat interface

    private BookRepository bookRepository;
    public SimpleBookStrategy() {
        this.bookRepository = new BookRepository();
    }

    @Override
    public Book update(Book book) {
        return bookRepository.update(book);
    }
}
