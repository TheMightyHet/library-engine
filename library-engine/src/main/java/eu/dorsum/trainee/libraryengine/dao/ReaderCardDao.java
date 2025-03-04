package eu.dorsum.trainee.libraryengine.dao;

import eu.dorsum.trainee.libraryengine.model.ReaderCard;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReaderCardDao {

    @Select("SELECT id, reader_id, valid_from, valid_until FROM gy_gy_reader_card WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "validFrom", column = "valid_from"),
            @Result(property = "validUntil", column = "valid_until")
    })
    ReaderCard findById(Long id);

    @Select("SELECT id, reader_id, valid_from, valid_until FROM gy_gy_reader_card WHERE reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "validFrom", column = "valid_from"),
            @Result(property = "validUntil", column = "valid_until")
    })
    List<ReaderCard> findByReaderId(Long readerId);

    @Select("SELECT id, reader_id, valid_from, valid_until FROM gy_gy_reader_card WHERE reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "validFrom", column = "valid_from"),
            @Result(property = "validUntil", column = "valid_until")
    })
    ReaderCard findValidCardByReaderId(Long readerId);

    @Insert("INSERT INTO gy_gy_reader_card (id, reader_id, valid_from, valid_until) VALUES (gy_gy_reader_card_seq.NEXTVAL, #{readerId}, #{validFrom}, #{validUntil})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(ReaderCard readerCard);

    @Update("UPDATE gy_gy_reader_card SET valid_from = #{validFrom}, valid_until = #{validUntil} WHERE id = #{id}")
    int update(ReaderCard readerCard);

    @Delete("DELETE FROM gy_gy_reader_card WHERE id = #{id}")
    int delete(Long id);
}
