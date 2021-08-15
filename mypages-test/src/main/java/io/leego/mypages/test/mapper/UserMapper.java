package io.leego.mypages.test.mapper;

import io.leego.mypages.test.dto.CustomClassParam;
import io.leego.mypages.test.dto.CustomCountParam;
import io.leego.mypages.test.dto.CustomMapParam;
import io.leego.mypages.test.dto.PageableParam;
import io.leego.mypages.test.dto.PaginationFieldParam;
import io.leego.mypages.test.dto.PaginationMethodParam;
import io.leego.mypages.test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Yihleego
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where status = #{status}")
    List<User> findByStatus(@Param("status") Integer status);

    @Select("select count(*) as total from user where status = #{status}")
    long countByStatus(@Param("status") Integer status);


    @Select("select * from user where status = #{status}")
    List<User> findByStatusWithPageSizeParam(@Param("page") Integer page, @Param("size") Integer size, @Param("status") Integer status);

    @Select("select * from user where status = #{status}")
    List<User> findByStatusOffsetRowsWithParam(@Param("offset") Long offset, @Param("rows") Integer rows, @Param("status") Integer status);


    @Select("select * from user where status = #{status}")
    List<User> findByPaginationField(PaginationFieldParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPaginationFieldWithParam0(@Param("param") PaginationFieldParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPaginationFieldWithParam1(@Param("param1") PaginationFieldParam param);


    @Select("select * from user where status = #{status}")
    List<User> findByPaginationMethod(PaginationMethodParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPaginationMethodWithParam0(@Param("param") PaginationMethodParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPaginationMethodWithParam1(@Param("param1") PaginationMethodParam param);


    @Select("select * from user where status = #{status}")
    List<User> findByPageable(PageableParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPageableWithParam0(@Param("param") PageableParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPageableWithParam1(@Param("param1") PageableParam param);


    @Select("select * from user where status = #{status}")
    List<User> findByCustomClass(CustomClassParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByCustomClassWithParam0(@Param("param") CustomClassParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCustomClassWithParam1(@Param("param1") CustomClassParam param);


    @Select("select * from user where status = #{status}")
    List<User> findByCustomMap(CustomMapParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByCustomMapWithParam0(@Param("param") CustomMapParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCustomMapWithParam1(@Param("param1") CustomMapParam param);


    @Select("select * from user where status = #{status}")
    List<User> findByCollectionMap(Map param);

    @Select("select * from user where status = #{map.status}")
    List<User> findByCollectionMapWithParam0(@Param("map") Map map);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCollectionMapWithParam1(@Param("param1") Map map);


    @Select("select * from user where status = #{status}")
    List<User> findByCustomCount(CustomCountParam param);
}
