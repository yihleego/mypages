package io.leego.mypages.spring.boot.autoconfigure;

import io.leego.mypages.interceptor.PaginationSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yihleego
 */
@ConfigurationProperties("mypages")
public class MyPagesProperties extends PaginationSettings {
}
