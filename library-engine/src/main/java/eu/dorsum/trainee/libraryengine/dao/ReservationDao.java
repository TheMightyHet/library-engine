package eu.dorsum.trainee.libraryengine.dao;

import eu.dorsum.trainee.libraryengine.model.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationDao {

    @Select("SELECT id, book_id, reader_id, reservation_date, expiration_date FROM gy_gy_reservation WHERE id = #{id}")
    Reservation findById(Long id);

    @Select("SELECT id, book_id, reader_id, reservation_date, expiration_date FROM gy_gy_reservation WHERE book_id = #{bookId}")
    Reservation findByBookId(Long bookId);

    @Select("SELECT id, book_id, reader_id, reservation_date, expiration_date FROM gy_gy_reservation WHERE reader_id = #{readerId}")
    List<Reservation> findByReaderId(Long readerId);

    @Select("SELECT id, book_id, reader_id, reservation_date, expiration_date FROM gy_gy_reservation WHERE book_id = #{bookId} AND reader_id = #{readerId}")
    Reservation findByBookAndReader(Long bookId, Long readerId);

    @Insert("INSERT INTO gy_gy_reservation (id, book_id, reader_id, reservation_date, expiration_date) " +
            "VALUES (gy_gy_reservation_seq.NEXTVAL, #{bookId}, #{readerId}, #{reservationDate}, #{expirationDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Reservation reservation);

    @Delete("DELETE FROM gy_gy_reservation WHERE id = #{id}")
    int delete(Long id);
}