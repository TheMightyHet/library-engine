package eu.dorsum.trainee.libraryengine.dao;

import eu.dorsum.trainee.libraryengine.model.Loan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoanDao {

    @Select("SELECT id, book_id, reader_id, loan_date, due_date, return_date FROM gy_gy_loan WHERE id = #{id}")
    Loan findById(Long id);

    @Insert("INSERT INTO gy_gy_loan (id, book_id, reader_id, loan_date, due_date, return_date) " +
            "VALUES (gy_gy_loan_seq.NEXTVAL, #{bookId}, #{readerId}, #{loanDate}, #{dueDate}, #{returnDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Loan loan);

    @Update("UPDATE gy_gy_loan SET return_date = #{returnDate} WHERE id = #{id}")
    int update(Loan loan);

    @Select("SELECT l.id, l.book_id, l.reader_id, l.loan_date, l.due_date, l.return_date " +
            "FROM gy_gy_loan l " +
            "WHERE l.reader_id = #{readerId} AND l.return_date IS NULL")
    List<Loan> findActiveByReaderId(Long readerId);

    @Select("SELECT l.id, l.book_id, l.reader_id, l.loan_date, l.due_date, l.return_date " +
            "FROM gy_gy_loan l " +
            "WHERE l.book_id = #{bookId} AND l.return_date IS NULL")
    Loan findActiveByBookId(Long bookId);

    @Select("SELECT l.id, l.book_id, l.reader_id, l.loan_date, l.due_date, l.return_date " +
            "FROM gy_gy_loan l " +
            "WHERE l.due_date < SYSDATE AND l.return_date IS NULL")
    List<Loan> findOverdue();
}
