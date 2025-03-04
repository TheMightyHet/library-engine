package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.BookDao;
import eu.dorsum.trainee.libraryengine.dao.ReservationDao;
import eu.dorsum.trainee.libraryengine.model.Book;
import eu.dorsum.trainee.libraryengine.model.BookStatus;
import eu.dorsum.trainee.libraryengine.model.Reservation;
import eu.dorsum.trainee.libraryengine.service.ReaderCardService;
import eu.dorsum.trainee.libraryengine.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final BookDao bookDao;
    private final ReaderCardService readerCardService;

    public ReservationServiceImpl(ReservationDao reservationDao, BookDao bookDao, ReaderCardService readerCardService) {
        this.reservationDao = reservationDao;
        this.bookDao = bookDao;
        this.readerCardService = readerCardService;
    }

    @Override
    public Reservation getReservation(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public Reservation getReservationForBook(Long bookId) {
        return reservationDao.findByBookId(bookId);
    }

    @Override
    public List<Reservation> getReservationsForReader(Long readerId) {
        return reservationDao.findByReaderId(readerId);
    }

    @Override
    @Transactional
    public Reservation reserveBook(Long bookId, Long readerId, int reservationDurationDays) {
        // Check if reader card is valid
        if (!readerCardService.isReaderCardValid(readerId)) {
            throw new IllegalStateException("Reader's card is not valid or expired");
        }

        // Check if book exists
        Book book = bookDao.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with id: " + bookId);
        }

        // Check if book is already reserved
        if (BookStatus.RESERVED.equals(book.getStatus())) {
            throw new IllegalStateException("Book is already reserved");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setBookId(bookId);
        reservation.setReaderId(readerId);
        reservation.setReservationDate(new Date());

        // Calculate expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservation.getReservationDate());
        calendar.add(Calendar.DAY_OF_MONTH, reservationDurationDays);
        reservation.setExpirationDate(calendar.getTime());

        // Save reservation
        reservationDao.insert(reservation);

        // Update book status if it's available (if it's borrowed, it will be marked as reserved when returned)
        if (BookStatus.AVAILABLE.equals(book.getStatus())) {
            book.setStatus(BookStatus.RESERVED);
            bookDao.update(book);
        }

        return reservation;
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found with id: " + reservationId);
        }

        // Delete reservation
        reservationDao.delete(reservationId);

        // Update book status if no active loan
        Book book = bookDao.findById(reservation.getBookId());
        if (BookStatus.RESERVED.equals(book.getStatus())) {
            book.setStatus(BookStatus.AVAILABLE);
            bookDao.update(book);
        }
    }

    @Override
    public boolean hasReaderReservedBook(Long bookId, Long readerId) {
        Reservation reservation = reservationDao.findByBookAndReader(bookId, readerId);
        return reservation != null;
    }
}