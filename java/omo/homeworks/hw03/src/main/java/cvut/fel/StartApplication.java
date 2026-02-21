package cvut.fel;

import cvut.fel.facade.BookFacadeImpl;
import cvut.fel.facade.dto.BookDto;
import cvut.fel.model.Author;
import cvut.fel.model.Book;
import cvut.fel.model.Library;
import cvut.fel.services.AuthorServiceImpl;
import cvut.fel.services.BookServiceImpl;
import cvut.fel.services.LibraryServiceImpl;
import cvut.fel.services.strategy.SimpleBookStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Only starts the application.
    There is old commented-out code in run() function - for testing purposes
     */
@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}