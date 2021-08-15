package io.leego.mypages.test.specification;

import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.test.dto.CustomMapParam;
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
public class CustomMapTests {
    private static final Logger logger = LoggerFactory.getLogger(CustomMapTests.class);
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
    public void findByCustomMapPageSize() {
        pageSizeFind(userMapper::findByCustomMap, pageSizeMap());
    }

    @Test
    public void findByCustomMapWithParam0PageSize() {
        pageSizeFind(userMapper::findByCustomMapWithParam0, pageSizeMap());
    }

    @Test
    public void findByCustomMapWithParam1PageSize() {
        pageSizeFind(userMapper::findByCustomMapWithParam1, pageSizeMap());
    }


    @Test
    public void findByCustomMapOffsetRows() {
        offsetRowsFind(userMapper::findByCustomMap, offsetRowsMap());
    }

    @Test
    public void findByCustomMapWithParam0OffsetRows() {
        offsetRowsFind(userMapper::findByCustomMapWithParam0, offsetRowsMap());
    }

    @Test
    public void findByCustomMapWithParam1OffsetRows() {
        offsetRowsFind(userMapper::findByCustomMapWithParam1, offsetRowsMap());
    }


    @Test
    public void findByCustomMapPageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomMap, pageSizeMap());
    }

    @Test
    public void findByCustomMapWithParam0PageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomMapWithParam0, pageSizeMap());
    }

    @Test
    public void findByCustomMapWithParam1PageSizeLoop() {
        pageSizeLoop(userMapper::findByCustomMapWithParam1, pageSizeMap());
    }


    @Test
    public void findByCustomMapOffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomMap, offsetRowsMap());
    }

    @Test
    public void findByCustomMapWithParam0OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomMapWithParam0, offsetRowsMap());
    }

    @Test
    public void findByCustomMapWithParam1OffsetRowsLoop() {
        offsetRowsLoop(userMapper::findByCustomMapWithParam1, offsetRowsMap());
    }


    public <T extends CustomMapParam> Page<User> pageSizeFind(Function<T, List<User>> function, T param) {
        List<User> list = function.apply(param);
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(param.get("size"), page.getList().size())
                : Objects.equals(param.get("size"), page.getSize()));
        Assertions.assertEquals(page.getPage(), param.get("page"));
        return page;
    }

    public <T extends CustomMapParam> Page<User> offsetRowsFind(Function<T, List<User>> function, T param) {
        List<User> list = function.apply(param);
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(param.get("rows"), page.getList().size())
                : Objects.equals(param.get("rows"), page.getSize()));
        Assertions.assertEquals((long) (page.getPage() - 1) * page.getSize(), param.get("offset"));
        return page;
    }

    public <T extends CustomMapParam> void pageSizeLoop(Function<T, List<User>> function, T param) {
        int i = 1;
        while (true) {
            param.put("page", i);
            Page<User> page = pageSizeFind(function, param);
            if (!page.getNext()) {
                break;
            }
            i++;
        }
    }

    public <T extends CustomMapParam> void offsetRowsLoop(Function<T, List<User>> function, T param) {
        long i = 0;
        while (true) {
            param.put("offset", i);
            Page<User> page = offsetRowsFind(function, param);
            if (!page.getNext()) {
                break;
            }
            i += (int) param.get("rows");
        }
    }

    public CustomMapParam pageSizeMap() {
        CustomMapParam map = new CustomMapParam();
        map.put("page", 1);
        map.put("size", 10);
        map.put("status", UserStatusEnum.ENABLED.getCode());
        return map;
    }

    public CustomMapParam offsetRowsMap() {
        CustomMapParam map = new CustomMapParam();
        map.put("offset", 0L);
        map.put("rows", 10);
        map.put("status", UserStatusEnum.ENABLED.getCode());
        return map;
    }

}
