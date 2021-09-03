package io.leego.mypages.sample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.leego.mypages.sample.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yihleego
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where status = #{status} and created_time <= #{createdTime}")
    List<User> query(@Param("page") Integer page,
                     @Param("size") Integer size,
                     @Param("status") Integer status,
                     @Param("createdTime") LocalDateTime createdTime);
}
