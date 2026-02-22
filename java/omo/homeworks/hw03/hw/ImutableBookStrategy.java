package cvut.fel.services.strategy;

import cvut.fel.dao.BookRepository;
import cvut.fel.model.Book;

public class ImutableBookStrategy implements BookStrategy {

    private BookRepository bookRepository;

    public ImutableBookStrategy() {
        this.bookRepository = new BookRepository();
    }

    //TODO Bude potřeba implementovat interface
    @Override
    public Book update (Book book) {
        Book newBook = new Book();

        newBook.setISBN(book.getISBN());
        newBook.setPublishingHouseId(book.getPublishingHouseId());
        newBook.setName(book.getName());
        newBook.setOwnerId(book.getOwnerId());
        newBook.setLibraryId(book.getLibraryId());
        newBook.setType(book.getType());

        newBook.setId(book.getId() + 1);

        bookRepository.save(newBook);
        return newBook;
    }
}
