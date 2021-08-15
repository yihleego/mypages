package io.leego.mypages.spring.boot.autoconfigure;

import io.leego.mypages.dialect.Database;
import io.leego.mypages.dialect.Dialect;
import io.leego.mypages.interceptor.PaginationInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Yihleego
 */
@Configuration
@ConditionalOnClass(Interceptor.class)
@ConditionalOnProperty(value = "mypages.enabled", matchIfMissing = true)
@EnableConfigurationProperties(MyPagesProperties.class)
public class MyPagesAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor(
            MyPagesProperties properties,
            @Autowired(required = false) Dialect dialect,
            @Autowired(required = false) DataSource dataSource) {
        if (properties.getDatabase() == null && dataSource != null) {
            properties.setDatabase(Database.fromDataSource(dataSource));
        }
        if (properties.getDatabase() == null && dialect == null) {
            throw new IllegalArgumentException("Please specify a database type.");
        }
        return new PaginationInterceptor(dialect != null ? dialect : properties.getDatabase().newDialect(), properties);
    }

}
