package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.BookDao;
import eu.dorsum.trainee.libraryengine.model.Book;
import eu.dorsum.trainee.libraryengine.model.BookStatus;
import eu.dorsum.trainee.libraryengine.service.BookService;
import eu.dorsum.trainee.libraryengine.service.LoanService;
import eu.dorsum.trainee.libraryengine.service.ReaderCardService;
import eu.dorsum.trainee.libraryengine.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getBook(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> getBooksByStatus(String status) {
        return bookDao.findByStatus(status);
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        // Default status is AVAILABLE for new books
        if (book.getStatus() == null) {
            book.setStatus(BookStatus.AVAILABLE);
        }
        bookDao.insert(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookDao.update(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        Book book = getBook(bookId);
        return book != null && BookStatus.AVAILABLE.equals(book.getStatus());
    }

    @Override
    public boolean isBookReserved(Long bookId) {
        Book book = getBook(bookId);
        return book != null && BookStatus.RESERVED.equals(book.getStatus());
    }

    @Override
    public boolean isBookBorrowed(Long bookId) {
        Book book = getBook(bookId);
        return book != null && BookStatus.BORROWED.equals(book.getStatus());
    }
}
