package io.leego.mypages.test.specification;

import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.test.dto.CustomClassParam;
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
public class CustomClassTests {
    private static final Logger logger = LoggerFactory.getLogger(CustomClassTests.class);
    @Autowired
    private PaginationInterceptor interceptor;
    @Autowired
    private UserMapper userMapper;
    private List<User> users;

    @BeforeEach
    public void before() {
        /* Configuration:
        mypages.page-field=page
        mypages.size-field=size
        mypages.offset-field=offset
        mypages.rows-field=rows*/
        interceptor.getSettings().setPageField("page");
        interceptor.getSettings().setSizeField("size");
        interceptor.getSettings().setOffsetField("offset");
        interceptor.getSettings().setRowsField("rows");
        users = userMapper.findByStatus(UserStatusEnum.ENABLED.getCode());
    }


    @Test
    public void findByCustomClassPageSize() {
        pageSizeFind(userMapper::findByCustomClass,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam0PageSize() {
        pageSizeFind(userMapper::findByCustomClassWithParam0,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam1PageSize() {
        pageSizeFind(userMapper::findByCustomClassWithParam1,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByCustomClassOffsetRows() {
        offsetRowsFind(userMapper::findByCustomClass,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam0OffsetRows() {
        offsetRowsFind(userMapper::findByCustomClassWithParam0,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam1OffsetRows() {
        offsetRowsFind(userMapper::findByCustomClassWithParam1,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByCustomClassPageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomClass,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam0PageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomClassWithParam0,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam1PageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomClassWithParam1,
                new CustomClassParam(1, 10, UserStatusEnum.ENABLED.getCode()));
    }


    @Test
    public void findByCustomClassOffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomClass,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam0OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomClassWithParam0,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }

    @Test
    public void findByCustomClassWithParam1OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomClassWithParam1,
                new CustomClassParam(0L, 10, UserStatusEnum.ENABLED.getCode()));
    }


    public <T extends CustomClassParam> Page<User> pageSizeFind(Function<T, List<User>> function, T param) {
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

    public <T extends CustomClassParam> Page<User> offsetRowsFind(Function<T, List<User>> function, T param) {
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

    public <T extends CustomClassParam> void pageSizeLoop(Function<T, List<User>> function, T param) {
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

    public <T extends CustomClassParam> void offsetRowsLoop(Function<T, List<User>> function, T param) {
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
