package io.leego.mypages.test.specification;

import io.leego.mypages.test.dto.PageableParam;
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
public class PageableTests {
    private static final Logger logger = LoggerFactory.getLogger(PageableTests.class);
    @Autowired
    private UserMapper userMapper;
    private List<User> users;

    @BeforeEach
    public void before() {
        users = userMapper.findByStatus(UserStatusEnum.ENABLED.getCode());
    }


    @Test
    public void findByPageablePageSize() {
        pageSizeFind(userMapper::findByPageable,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam0PageSize() {
        pageSizeFind(userMapper::findByPageableWithParam0,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam1PageSize() {
        pageSizeFind(userMapper::findByPageableWithParam1,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPageableOffsetRows() {
        offsetRowsFind(userMapper::findByPageable,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam0OffsetRows() {
        offsetRowsFind(userMapper::findByPageableWithParam0,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam1OffsetRows() {
        offsetRowsFind(userMapper::findByPageableWithParam1,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPageablePageSizeLoop() {
        pageSizeLoop(userMapper::findByPageable,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam0PageSizeLoop() {
        pageSizeLoop(userMapper::findByPageableWithParam0,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam1PageSizeLoop() {
        pageSizeLoop(userMapper::findByPageableWithParam1,
                new PageableParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByPageableOffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPageable,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam0OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPageableWithParam0,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByPageableWithParam1OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByPageableWithParam1,
                new PageableParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    public <T extends PageableParam> Page<User> pageSizeFind(Function<T, List<User>> function, T param) {
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

    public <T extends PageableParam> Page<User> offsetRowsFind(Function<T, List<User>> function, T param) {
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

    public <T extends PageableParam> void pageSizeLoop(Function<T, List<User>> function, T param) {
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

    public <T extends PageableParam> void offsetRowsLoop(Function<T, List<User>> function, T param) {
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
