package io.leego.mypages.test.specification;

import io.leego.mypages.interceptor.PaginationInterceptor;
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

/**
 * @author Yihleego
 */
@SpringBootTest
public class MybatisParamTests {
    private static final Logger logger = LoggerFactory.getLogger(MybatisParamTests.class);
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
    public void findByStatusWithPageSizeParam() {
        pageSizeFind(1, 10);
    }

    @Test
    public void findByStatusOffsetRowsWithParam() {
        offsetRowsFind(0L, 10);
    }


    @Test
    public void findByStatusWithPageSizeParamLoop() {
        pageSizeLoop();
    }

    @Test
    public void findByStatusOffsetRowsWithParamLoop() {
        offsetRowsLoop();
    }


    public Page<User> pageSizeFind(Integer paramPage, Integer paramSize) {
        List<User> list = userMapper.findByStatusWithPageSizeParam(paramPage, paramSize, UserStatusEnum.ENABLED.getCode());
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(paramSize, page.getList().size())
                : Objects.equals(paramSize, page.getSize()));
        Assertions.assertEquals(page.getPage(), paramPage);
        return page;
    }

    public Page<User> offsetRowsFind(Long paramOffset, Integer paramRows) {
        List<User> list = userMapper.findByStatusOffsetRowsWithParam(paramOffset, paramRows, UserStatusEnum.ENABLED.getCode());
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(paramRows, page.getList().size())
                : Objects.equals(paramRows, page.getSize()));
        Assertions.assertEquals((long) (page.getPage() - 1) * page.getSize(), paramOffset);
        return page;
    }

    public <T extends PageableParam> void pageSizeLoop() {
        int i = 1;
        while (true) {
            Page<User> page = pageSizeFind(i, 10);
            if (!page.getNext()) {
                break;
            }
            i++;
        }
    }

    public <T extends PageableParam> void offsetRowsLoop() {
        long i = 0;
        while (true) {
            Page<User> page = offsetRowsFind(i, 10);
            if (!page.getNext()) {
                break;
            }
            i += 10;
        }
    }
}
