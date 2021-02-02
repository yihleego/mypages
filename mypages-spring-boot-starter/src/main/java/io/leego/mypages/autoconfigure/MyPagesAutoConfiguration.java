package io.leego.mypages.autoconfigure;

import io.leego.mypages.dialect.Dialect;
import io.leego.mypages.interceptor.PaginationInterceptor;
import io.leego.mypages.interceptor.PaginationSettings;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Yihleego
 */
@Configuration
@ConditionalOnProperty(value = "spring.mypages.enabled", matchIfMissing = true)
@EnableConfigurationProperties(MyPagesProperties.class)
public class MyPagesAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor(MyPagesProperties properties, @Autowired(required = false) Dialect dialect) {
        if (properties.getSqlDialect() == null && dialect == null) {
            throw new IllegalArgumentException("SqlDialect is required.");
        }
        PaginationSettings settings = PaginationSettings.builder()
                .dialect(dialect)
                .sqlDialect(properties.getSqlDialect())
                .countExpr(properties.getCountExpr())
                .pageField(properties.getPageField())
                .sizeField(properties.getSizeField())
                .offsetField(properties.getOffsetField())
                .rowsField(properties.getRowsField())
                .countExprField(properties.getCountExprField())
                .countMethodNameField(properties.getCountMethodNameField())
                .enableCountField(properties.getEnableCountField())
                .skipQueryIfCountEqualsZero(properties.isSkipQueryIfCountEqualsZero())
                .useGeneratedIfCountMethodIsMissing(properties.isUseGeneratedIfCountMethodIsMissing())
                .defaultPage(properties.getDefaultPage())
                .defaultSize(properties.getDefaultSize())
                .maxPage(properties.getMaxPage())
                .maxSize(properties.getMaxSize())
                .build();
        return new PaginationInterceptor(settings);
    }

    @Configuration
    @ConditionalOnBean(SqlSessionFactory.class)
    public static class PaginationInterceptorConfiguration implements InitializingBean {
        @Autowired(required = false)
        private List<SqlSessionFactory> sqlSessionFactories;
        @Autowired(required = false)
        private PaginationInterceptor paginationInterceptor;

        @Override
        public void afterPropertiesSet() {
            if (sqlSessionFactories == null || paginationInterceptor == null) {
                return;
            }
            for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
                List<Interceptor> interceptors = sqlSessionFactory.getConfiguration().getInterceptors();
                if (isAbsent(interceptors)) {
                    sqlSessionFactory.getConfiguration().addInterceptor(paginationInterceptor);
                }
            }
        }

        private boolean isAbsent(List<Interceptor> interceptors) {
            for (Interceptor interceptor : interceptors) {
                if (interceptor instanceof PaginationInterceptor) {
                    return false;
                }
            }
            return true;
        }
    }

}
