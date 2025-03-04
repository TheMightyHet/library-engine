package eu.dorsum.trainee.libraryengine.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestDao {

    @Select("SELECT 1 FROM dual")
    int getOne();

    int getTwo();
}
