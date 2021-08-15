package io.leego.mypages.test.specification;

import io.leego.mypages.test.dto.PaginationMethodParam;
import io.leego.mypages.test.entity.User;
import io.leego.mypages.test.enums.UserStatusEnum;
import io.leego.mypages.test.mapper.UserMapper;
import io.leego.mypages.util.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Yihleego
 */
@SpringBootTest
public class PaginationMethodTests {
    private static final Logger logger = LoggerFactory.getLogger(PaginationMethodTests.class);
    @Autowired
    private UserMapper userMapper;
    private List<User> users;

    @BeforeEach
    public void before() {
        users = userMapper.findByStatus(UserStatusEnum.ENABLED.getCode());
    }


    @Test
    public void findByPaginationMethodPageSize() {
        pageSizeFind(userMapper::findByPaginationMethod,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam0PageSize() {
        pageSizeFind(userMapper::findByPaginationMethodWithParam0,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam1PageSize() {
        pageSizeFind(userMapper::findByPaginationMethodWithParam1,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationMethodOffsetRows() {
        offsetRowsFind(userMapper::findByPaginationMethod,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam0OffsetRows() {
        offsetRowsFind(userMapper::findByPaginationMethodWithParam0,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam1OffsetRows() {
        offsetRowsFind(userMapper::findByPaginationMethodWithParam1,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationMethodPageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationMethod,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam0PageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationMethodWithParam0,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam1PageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationMethodWithParam1,
                new PaginationMethodParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationMethodOffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationMethod,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam0OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationMethodWithParam0,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationMethodWithParam1OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationMethodWithParam1,
                new PaginationMethodParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    public <T extends PaginationMethodParam> Page<User> pageSizeFind(Function<T, List<User>> function, T param) {
        List<User> list = function.apply(param);
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(param.getSize(), page.getList().size())
                : Objects.equals(param.getSize(), page.getSize()));
        Assertions.assertEquals(page.getPage(), param.getPage());
        return page;
    }

    public <T extends PaginationMethodParam> Page<User> offsetRowsFind(Function<T, List<User>> function, T param) {
        List<User> list = function.apply(param);
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(param.getRows(), page.getList().size())
                : Objects.equals(param.getRows(), page.getSize()));
        Assertions.assertEquals((long) (page.getPage() - 1) * page.getSize(), param.getOffset());
        return page;
    }

    public <T extends PaginationMethodParam> void pageSizeLoop(Function<T, List<User>> function, T param) {
        int i = 1;
        while (true) {
            param.setPage(i);
            Page<User> page = pageSizeFind(function, param);
            if (!page.getNext()) {
                break;
            }
            i++;
        }
    }

    public <T extends PaginationMethodParam> void offsetRowsLoop(Function<T, List<User>> function, T param) {
        long i = 0;
        while (true) {
            param.setOffset(i);
            Page<User> page = offsetRowsFind(function, param);
            if (!page.getNext()) {
                break;
            }
            i += param.getRows();
        }
    }
}
