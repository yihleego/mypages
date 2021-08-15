package io.leego.mypages.sample;

import io.leego.mypages.sample.entity.User;
import io.leego.mypages.sample.mapper.UserMapper;
import io.leego.mypages.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Yihleego
 */
public class MyPagesApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyPagesApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring.xml");
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        List<User> list = userMapper.query(1, 10);
        Page<User> page = Page.of(list);
        logger.info("{}", page);
    }

}
