package io.leego.mypages.test.specification;

import io.leego.mypages.test.dto.PaginationFieldParam;
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
public class PaginationFieldTests {
    private static final Logger logger = LoggerFactory.getLogger(PaginationFieldTests.class);
    @Autowired
    private UserMapper userMapper;
    private List<User> users;

    @BeforeEach
    public void before() {
        users = userMapper.findByStatus(UserStatusEnum.ENABLED.getCode());
    }


    @Test
    public void findByPaginationFieldPageSize() {
        pageSizeFind(userMapper::findByPaginationField,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam0PageSize() {
        pageSizeFind(userMapper::findByPaginationFieldWithParam0,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam1PageSize() {
        pageSizeFind(userMapper::findByPaginationFieldWithParam1,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationFieldOffsetRows() {
        offsetRowsFind(userMapper::findByPaginationField,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam0OffsetRows() {
        offsetRowsFind(userMapper::findByPaginationFieldWithParam0,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam1OffsetRows() {
        offsetRowsFind(userMapper::findByPaginationFieldWithParam1,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationFieldPageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationField,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam0PageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationFieldWithParam0,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam1PageSizeLoop() {
        pageSizeLoop(userMapper::findByPaginationFieldWithParam1,
                new PaginationFieldParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPaginationFieldOffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationField,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam0OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationFieldWithParam0,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPaginationFieldWithParam1OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPaginationFieldWithParam1,
                new PaginationFieldParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    public <T extends PaginationFieldParam> Page<User> pageSizeFind(Function<T, List<User>> function, T param) {
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

    public <T extends PaginationFieldParam> Page<User> offsetRowsFind(Function<T, List<User>> function, T param) {
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

    public <T extends PaginationFieldParam> void pageSizeLoop(Function<T, List<User>> function, T param) {
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

    public <T extends PaginationFieldParam> void offsetRowsLoop(Function<T, List<User>> function, T param) {
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
