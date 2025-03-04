package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.model.Reservation;
import eu.dorsum.trainee.libraryengine.service.BookService;
import eu.dorsum.trainee.libraryengine.service.ReaderService;
import eu.dorsum.trainee.libraryengine.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final BookService bookService;
    private final ReaderService readerService;

    public ReservationController(ReservationService reservationService, BookService bookService, ReaderService readerService) {
        this.reservationService = reservationService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/getReservationByID/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getReservationForBookByID/{bookId}")
    public ResponseEntity<Reservation> getReservationForBook(@PathVariable Long bookId) {
        if (bookService.getBook(bookId) == null) {
            return ResponseEntity.notFound().build();
        }

        Reservation reservation = reservationService.getReservationForBook(bookId);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getReservationsForReaderByID/{readerId}")
    public ResponseEntity<List<Reservation>> getReservationsForReader(@PathVariable Long readerId) {
        if (readerService.getReader(readerId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<Reservation> reservations = reservationService.getReservationsForReader(readerId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reserveBookByID")
    public ResponseEntity<?> reserveBook(@RequestParam Long bookId, @RequestParam Long readerId, @RequestParam(defaultValue = "7") int reservationDurationDays) {
        try {
            if (bookService.getBook(bookId) == null) {
                return ResponseEntity.badRequest().body("Book not found");
            }

            if (readerService.getReader(readerId) == null) {
                return ResponseEntity.badRequest().body("Reader not found");
            }

            Reservation reservation = reservationService.reserveBook(bookId, readerId, reservationDurationDays);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancelReservationByID/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservation(id);
            if (reservation == null) {
                return ResponseEntity.notFound().build();
            }

            reservationService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}