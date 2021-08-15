package io.leego.mypages.sample;

import io.leego.mypages.dialect.Database;
import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.interceptor.PaginationSettings;
import io.leego.mypages.sample.entity.User;
import io.leego.mypages.sample.mapper.UserMapper;
import io.leego.mypages.util.Page;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Yihleego
 */
@MapperScan("io.leego.mypages.sample")
@SpringBootApplication
public class MyPagesApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyPagesApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MyPagesApplication.class, args);
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        List<User> list = userMapper.query(1, 10);
        Page<User> page = Page.of(list);
        logger.info("{}", page);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        Interceptor[] plugins = new Interceptor[]{paginationInterceptor(dataSource)};
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(DataSource dataSource) {
        PaginationSettings settings = PaginationSettings.builder()
                .database(Database.fromDataSource(dataSource))
                .pageField("page")
                .sizeField("size")
                .build();
        return new PaginationInterceptor(settings);
    }
}
