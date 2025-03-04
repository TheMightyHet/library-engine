package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.model.Book;
import java.util.List;

public interface BookService {
    Book getBook(Long id);
    List<Book> getAllBooks();
    List<Book> getBooksByStatus(String status);
    Book createBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long id);
    boolean isBookAvailable(Long bookId);
    boolean isBookReserved(Long bookId);
    boolean isBookBorrowed(Long bookId);
}
