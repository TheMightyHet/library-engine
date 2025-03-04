package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.model.Reservation;
import java.util.List;

public interface ReservationService {
    Reservation getReservation(Long id);
    Reservation getReservationForBook(Long bookId);
    List<Reservation> getReservationsForReader(Long readerId);
    Reservation reserveBook(Long bookId, Long readerId, int reservationDurationDays);
    void cancelReservation(Long reservationId);
    boolean hasReaderReservedBook(Long bookId, Long readerId);
}