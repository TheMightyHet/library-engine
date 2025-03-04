package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.BookDao;
import eu.dorsum.trainee.libraryengine.dao.LoanDao;
import eu.dorsum.trainee.libraryengine.dao.ReservationDao;
import eu.dorsum.trainee.libraryengine.model.Book;
import eu.dorsum.trainee.libraryengine.model.BookStatus;
import eu.dorsum.trainee.libraryengine.model.Loan;
import eu.dorsum.trainee.libraryengine.model.Reservation;
import eu.dorsum.trainee.libraryengine.service.LoanService;
import eu.dorsum.trainee.libraryengine.service.ReaderCardService;
import eu.dorsum.trainee.libraryengine.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao;
    private final BookDao bookDao;
    private final ReservationDao reservationDao;
    private final ReaderCardService readerCardService;

    public LoanServiceImpl(LoanDao loanDao, BookDao bookDao, ReservationDao reservationDao, ReaderCardService readerCardService) {
        this.loanDao = loanDao;
        this.bookDao = bookDao;
        this.reservationDao = reservationDao;
        this.readerCardService = readerCardService;
    }

    @Override
    public Loan getLoan(Long id) {
        return loanDao.findById(id);
    }

    @Override
    public List<Loan> getActiveLoansForReader(Long readerId) {
        return loanDao.findActiveByReaderId(readerId);
    }

    @Override
    public Loan getActiveLoanForBook(Long bookId) {
        return loanDao.findActiveByBookId(bookId);
    }

    @Override
    public List<Loan> getOverdueLoans() {
        return loanDao.findOverdue();
    }

    @Override
    @Transactional
    public Loan borrowBook(Long bookId, Long readerId, int loanDurationDays) {
        // Check if reader card is valid
        if (!readerCardService.isReaderCardValid(readerId)) {
            throw new IllegalStateException("Reader's card is not valid or expired");
        }

        Book book = bookDao.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with id: " + bookId);
        }

        // Check if book is available or if this reader has reserved it
        boolean canBorrow = false;

        if (BookStatus.AVAILABLE.equals(book.getStatus())) {
            canBorrow = true;
        } else if (BookStatus.RESERVED.equals(book.getStatus())) {
            Reservation reservation = reservationDao.findByBookId(bookId);
            if (reservation != null && reservation.getReaderId().equals(readerId)) {
                canBorrow = true;
                // Delete the reservation as it's being converted to a loan
                reservationDao.delete(reservation.getId());
            }
        }

        if (!canBorrow) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        // Create new loan
        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setReaderId(readerId);
        loan.setLoanDate(new Date());

        // Calculate due date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getLoanDate());
        calendar.add(Calendar.DAY_OF_MONTH, loanDurationDays);
        loan.setDueDate(calendar.getTime());

        // Save loan
        loanDao.insert(loan);

        // Update book status
        book.setStatus(BookStatus.BORROWED);
        bookDao.update(book);

        return loan;
    }

    @Override
    @Transactional
    public Loan returnBook(Long bookId) {
        Loan loan = loanDao.findActiveByBookId(bookId);
        if (loan == null) {
            throw new IllegalArgumentException("No active loan found for book with id: " + bookId);
        }

        // Update return date
        loan.setReturnDate(new Date());
        loanDao.update(loan);

        // Check if there's a reservation for this book
        Reservation reservation = reservationDao.findByBookId(bookId);

        // Update book status
        Book book = bookDao.findById(bookId);
        book.setStatus(reservation != null ? BookStatus.RESERVED : BookStatus.AVAILABLE);
        bookDao.update(book);

        return loan;
    }
}
