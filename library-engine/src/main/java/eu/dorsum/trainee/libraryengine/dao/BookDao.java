package eu.dorsum.trainee.libraryengine.dao;

import eu.dorsum.trainee.libraryengine.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookDao {

    @Select("SELECT id, title, status FROM gy_gy_book WHERE id = #{id}")
    Book findById(Long id);

    @Select("SELECT id, title, status FROM gy_gy_book")
    List<Book> findAll();

    @Select("SELECT id, title, status FROM gy_gy_book WHERE status = #{status}")
    List<Book> findByStatus(String status);

    @Insert("INSERT INTO gy_gy_book (id, title, status) VALUES (gy_gy_book_seq.NEXTVAL, #{title}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Book book);

    @Update("UPDATE gy_gy_book SET title = #{title}, status = #{status} WHERE id = #{id}")
    int update(Book book);

    @Delete("DELETE FROM gy_gy_book WHERE id = #{id}")
    int delete(Long id);
}
