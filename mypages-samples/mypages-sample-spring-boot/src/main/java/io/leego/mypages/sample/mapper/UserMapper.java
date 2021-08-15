package io.leego.mypages.sample.mapper;

import io.leego.mypages.sample.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yihleego
 */
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> query(@Param("page") Integer page, @Param("size") Integer size);
}
