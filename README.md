# MyPages

The MyPages is a java based, open source pagination plugin for MyBatis that simplifies database paging queries.

## Dependency

```bash
# git clone https://github.com/yihleego/mypages.git
# cd mypages
```

### Maven

```xml
<properties>
    <mypages.version>0.0.1</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:0.0.1'
```

## Usage

### Spring Xml
```xml
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="mapperLocations">
        <list>
            <value>classpath:mapper/*.xml</value>
        </list>
    </property>
    <property name="plugins">
        <bean class="io.leego.mypages.interceptor.PaginationInterceptor">
            <property name="sqlDialect" value="MYSQL"/>
            <property name="pageFieldName" value="page"/>
            <property name="sizeFieldName" value="size"/>
        </bean>
    </property>
</bean>
```

### Spring Boot 

```java
@Bean
public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    // Plugins
    Interceptor[] plugins = new Interceptor[1];
    plugins[0] = interceptor();
    // SqlSessionFactoryBean
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setPlugins(plugins);
    return sqlSessionFactoryBean.getObject();
}

@Bean
public PaginationInterceptor interceptor() {
    return new PaginationInterceptor()
            .sqlDialect(SqlDialect.POSTGRESQL)
            .pagingFields("page", "size");
}
```


## Documentation

> * Soon...

## Contact

> * Bugs: [Issues](https://github.com/yihleego/mypages/issues)


## License
MyPages is under the Apache 2.0 license. See the [LICENSE](https://github.com/yihleego/mypages/blob/master/LICENSE.txt) file for details.

