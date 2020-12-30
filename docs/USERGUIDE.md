# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 and above.

# 3. Dependency

## 3.1 Maven

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>1.0.1</version>
</dependency>
```

## 3.2 Gradle

```xml
implementation 'io.leego:mypages:1.0.1'
```

# 4. Quick Setup

Please notice that the PaginationInterceptor requires SqlDialect.

## 4.1 MyBatis SqlSessionFactoryBean

```java
PaginationSettings settings = new PaginationSettings(SqlDialect.MYSQL);
PaginationInterceptor paginationInterceptor = new PaginationInterceptor(settings);
Interceptor[] plugins = new Interceptor[]{paginationInterceptor};
SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
sqlSessionFactoryBean.setPlugins(plugins);
```

## 4.2 Spring

To use MyBatis with Spring you need at least two things defined in the Spring application context: an SqlSessionFactory and at least one mapper interface.

In MyBatis-Spring, an SqlSessionFactoryBean is used to create an SqlSessionFactory. To configure the factory bean, put the following in the Spring configuration file:

## 4.3 Spring XML

```xml
<bean id="paginationInterceptor" class="io.leego.mypages.interceptor.PaginationInterceptor">
    <constructor-arg name="settings">
        <bean class="io.leego.mypages.interceptor.PaginationSettings">
            <property name="sqlDialect" value="MYSQL"/>
        </bean>
    </constructor-arg>
</bean>

<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins" value="paginationInterceptor"/>
</bean>
```

## 4.4 Spring Boot

```java
@Configuration
public class MybatisConfiguration {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        Interceptor[] plugins = new Interceptor[]{paginationInterceptor()};
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationSettings settings = new PaginationSettings(SqlDialect.MYSQL);
        return new PaginationInterceptor(settings);
    }
}
```

## 4.5 Spring Boot Starter

> * Please see: [mypages-spring-boot-starter](STARTER_USERGUIDE.md)

# 5. Quick Start

Assume there is a class defined like the following:

```java
import io.leego.mypages.annotation.Pagination;
import io.leego.mypages.annotation.Page;
import io.leego.mypages.annotation.Size;

@Pagination
public class Pageable {
    @Page
    private Integer page;
    @Size
    private Integer size;

    public Pageable(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
    /* getter setter */
}
```

Assume there is a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Pageable pageable);
}
```

Call query method:

```java
import io.leego.mypages.util.Page;

@Service
public class QueryTests {
    @Autowired
    private FooMapper fooMapper;

    public Page<Foo> query() {
        return Page.of(fooMapper.query(new Pageable(1, 10)));
    }
}
```

# 6. Enable Pagination

## 6.1 Annotations (Recommended)

Using `@Pagination`, `@Page`, `@Size`, `@Offset`, `@Rows` annotations.

```java
@Pagination
public class Pageable {
    @Page
    private Integer page;
    @Size
    private Integer size;
    @Offset
    private Integer offset;
    @Rows
    private Integer rows;
    /* getter setter */
}
```

More annotations: `@CountExpr`, `@CountMethodName`, `@DisableCount`, `@DisablePagination`.

## 6.2 PaginationInterceptor Configuration (Recommended)

Configure the interceptor.

```java
PaginationSettings settings = PaginationSettings.builder()
    .sqlDialect(SqlDialect.MYSQL)
    .pageField("page")
    .sizeField("size")
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

### 6.2.1 Define classes

Define a class with paging parameters.

```java
public class Pageable {
    private Integer page;
    private Integer size;

    public Pageable(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
    /* getter setter */
}
```

Assume there is a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Pageable pageable);
}
```

Call query method:

```java
import io.leego.mypages.util.Page;

@Service
public class QueryTests {
    @Autowired
    private FooMapper fooMapper;

    public Page<Foo> query() {
        return Page.of(fooMapper.query(new Pageable(1, 10)));
    }
}
```

### 6.2.2 MyBatis `@Param`

Assume there is a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(@Param("page") Integer page, @Param("size") Integer size);
}
```

Call query method:

```java
import io.leego.mypages.util.Page;

@Service
public class QueryTests {
    @Autowired
    private FooMapper fooMapper;

    public Page<Foo> query() {
        return Page.of(fooMapper.query(1, 10));
    }
}
```

### 6.2.3 Map

Assume there is a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Map map);
}
```

Call query method:

```java
import io.leego.mypages.util.Page;

@Service
public class QueryTests {
    @Autowired
    private FooMapper fooMapper;

    public Page<Foo> query() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", 1);
        map.put("page", 10);
        return Page.of(fooMapper.query(map));
    }
}
```

### More configurations.

```java
PaginationSettings settings = PaginationSettings.builder()
    .sqlDialect(SqlDialect.MYSQL) // Specifies sql-dialect.
    .countExpr("*") // The column name or expression, the default value is <code>"*"</code>.
    .pageField("page") // Obtains the page value from parameter.
    .sizeField("size") // Obtains the size value from parameter.
    .offsetField("offset") // Obtains the offset value from parameter.
    .rowsField("rows") // Obtains the rows value from parameter.
    .countExprField("countExpr") // Obtains the count expression from parameter.
    .countMethodNameField("countMethodName") // Obtains the count-method-name from parameter.
    .enableCountField("enableCount") // Obtains the enable-count from parameter.
    .skipQueryIfCountEqualsZero(true) // Whether to skip query if total quantity equals zero.
    .useGeneratedIfCountMethodIsMissing(true) // Whether to use generated if the specified count method is missing.
    .defaultPage(1) // Replaces the page with default-page if the page is null or less than 1.
    .defaultSize(10) // Replaces the size with default-size if the size is null or less than 1.
    .maxPage(10000) // Replaces the page with max-page if the page is greater than max-page.
    .maxSize(10000) // Replaces the size with max-size if the size is greater than max-size.
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

## 6.3 Implements `io.leego.mypages.util.Pageable`

Define a class implements `io.leego.mypages.util.Pageable`.

```java
public class PageRequest implements io.leego.mypages.util.Pageable {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer rows;

    @Override
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
```

# 7. Using custom count methods

## 7.1 Annotations

```java
@Pagination
public class Pageable {
    @Page
    private Integer page;
    @Size
    private Integer size;
    @CountMethodName
    private String countMethodName;

    public Pageable(Integer page, Integer size, String countMethodName) {
        this.page = page;
        this.size = size;
        this.countMethodName = countMethodName;
    }
    /* getter setter */
}
```

## 7.2 PaginationInterceptor Configuration

```java
public class Pageable {
    private Integer page;
    private Integer size;
    private String countMethodName;

    public Pageable(Integer page, Integer size, String countMethodName) {
        this.page = page;
        this.size = size;
        this.countMethodName = countMethodName;
    }
    /* getter setter */
}
```

```java
PaginationSettings settings = PaginationSettings.builder()
    .sqlDialect(SqlDialect.MYSQL)
    .pageField("page")
    .sizeField("size")
    .countMethodNameField("countMethodName")
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

## 7.3 Call methods:

Assume there is a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Pageable pageable);
    
    @Select("SELECT COUNT(*) FROM foo")
    long count(Pageable pageable);
}
```

Specify the count method name:

```java
import io.leego.mypages.util.Page;

@Service
public class QueryTests {
    @Autowired
    private FooMapper fooMapper;

    public Page<Foo> query() {
        // Specifies the count method name.
        return Page.of(fooMapper.query(new Pageable(1, 10, "count")));
    }
}
```

# 8. Parameters rationalization
  
Rationalize the parameters if the values are invalid, the following parameters can be set:

**defaultPage**: Replaces the page with default-page if the page is null or less than 1.  
**defaultSize**: Replaces the size with default-size if the size is null or less than 1.  
**maxPage**: Replaces the page with max-page if the page is greater than max-page.  
**maxSize**: Replaces the size with max-size if the size is greater than max-size.  

## 8.1 Annotations

```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 10000, maxSize = 10000)
public class Pageable {
}
```

## 8.2 PaginationInterceptor Configuration

```java
PaginationSettings settings = PaginationSettings.builder()
    .sqlDialect(SqlDialect.MYSQL)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(10000)
    .maxSize(10000)
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

# 9. Query Results

If the invocation proceed, it will return an instance of the `PaginationCollection`.

## 9.1 Using `io.leego.mypages.util.Page` 

#### 9.1.1 Wrap

```java
Page<Foo> result = Page.of(fooMapper.query(pageable));
```

#### 9.1.2 Convert

```java
Page<Bar> result = Page.of(fooMapper.query(pageable), foo -> new Bar(foo));
```

#### 9.1.3 Mapping

```java
Page<Bar> result = Page.of(fooMapper.query(pageable)).map(foo -> new Bar(foo));
```
