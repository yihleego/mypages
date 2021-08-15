package io.leego.mypages.test.specification;

import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.test.dto.CustomCountParam;
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
public class CustomCountTests {
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
        mypages.count-method-name-field=countMethodName*/
        interceptor.getSettings().setPageField("page");
        interceptor.getSettings().setSizeField("size");
        interceptor.getSettings().setCountMethodNameField("countMethodName");
        users = userMapper.findByStatus(UserStatusEnum.ENABLED.getCode());
    }


    @Test
    public void findByCustomCount() {
        CustomCountParam param = new CustomCountParam(1, 10, "countByStatus", UserStatusEnum.ENABLED.getCode());
        List<User> list = userMapper.findByCustomCount(param);
        Page<User> page = Page.of(list);
        logger.debug("{}", page);
        Assertions.assertEquals(users.size(), page.getTotal());
        Assertions.assertTrue(page.isPaged() && !page.isEmpty());
        Assertions.assertTrue(page.getNext()
                ? Objects.equals(param.getSize(), page.getList().size())
                : Objects.equals(param.getSize(), page.getSize()));
        Assertions.assertEquals(page.getPage(), param.getPage());
    }

}
