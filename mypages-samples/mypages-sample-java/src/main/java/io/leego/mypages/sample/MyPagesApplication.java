package io.leego.mypages.sample;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.leego.mypages.dialect.Database;
import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.interceptor.PaginationSettings;
import io.leego.mypages.sample.entity.User;
import io.leego.mypages.sample.mapper.UserMapper;
import io.leego.mypages.util.Page;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yihleego
 */
public class MyPagesApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyPagesApplication.class);

    public static void main(String[] args) {
        new MyPagesApplication().run();
    }

    public void run() {
        DataSource dataSource = dataSource();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory(dataSource);
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int i = 0;
            while (true) {
                List<User> list = userMapper.query(++i, 10, 1, LocalDateTime.now());
                Page<User> page = Page.of(list);
                logger.info("{}", page);
                if (!page.getNext()) {
                    break;
                }
            }
        }
    }

    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mypages?serverTimezone=GMT%2B8");
        config.setUsername("root");
        config.setPassword("123456");
        return new HikariDataSource(config);
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(SqlSessionFactory.class.getSimpleName(), transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(UserMapper.class);
        configuration.addInterceptor(paginationInterceptor(dataSource));
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    public PaginationInterceptor paginationInterceptor(DataSource dataSource) {
        PaginationSettings settings = PaginationSettings.builder()
                .database(Database.fromDataSource(dataSource))
                .pageField("page")
                .sizeField("size")
                .build();
        return new PaginationInterceptor(settings);
    }
}
