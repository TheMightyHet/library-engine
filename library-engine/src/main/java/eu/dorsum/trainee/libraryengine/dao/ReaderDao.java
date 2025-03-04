package eu.dorsum.trainee.libraryengine.dao;

import eu.dorsum.trainee.libraryengine.model.Reader;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReaderDao {

    @Select("SELECT id, name FROM gy_gy_reader WHERE id = #{id}")
    Reader findById(Long id);

    @Select("SELECT id, name FROM gy_gy_reader")
    List<Reader> findAll();

    @Insert("INSERT INTO gy_gy_reader (id, name) VALUES (gy_gy_reader_seq.NEXTVAL, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Reader reader);

    @Update("UPDATE gy_gy_reader SET name = #{name} WHERE id = #{id}")
    int update(Reader reader);

    @Delete("DELETE FROM gy_gy_reader WHERE id = #{id}")
    int delete(Long id);
}
