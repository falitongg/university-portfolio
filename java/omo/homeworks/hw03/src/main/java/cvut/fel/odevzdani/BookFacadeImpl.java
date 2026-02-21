package cvut.fel.facade;


import cvut.fel.facade.dto.BookDto;
import cvut.fel.facade.mapper.BookMapper;
import cvut.fel.model.Author;
import cvut.fel.model.Book;
import cvut.fel.model.Library;
import cvut.fel.services.AuthorServiceImpl;
import cvut.fel.services.BookServiceImpl;
import cvut.fel.services.LibraryServiceImpl;

// TODO - asi tu bude potřeba něco implementovat
public class BookFacadeImpl implements BookFacade {

    // TODO Doplnit kód, co všechno budete potřebovat k nalezení knihy v systému?

    private AuthorServiceImpl authorServiceImpl;
    private BookServiceImpl bookServiceImpl;
    private LibraryServiceImpl libraryServiceImpl;

    public BookFacadeImpl(){
        this.authorServiceImpl = new AuthorServiceImpl();
        this.bookServiceImpl = new BookServiceImpl();
        this.libraryServiceImpl = new LibraryServiceImpl();
    }

    @Override
    public BookDto getByBookId(int bookId) {
        Book book = bookServiceImpl.getByBookId(bookId);
        Author author = authorServiceImpl.getByBookId(bookId);
        Library library = libraryServiceImpl.getByBookId(bookId);

        return BookMapper.toDto(book, author, library);
    }
}
