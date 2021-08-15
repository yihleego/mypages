# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 or above.

# 3. Maven Dependency

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

# 4. Quick Start

Assume there are schema and table based on `MySQL`:

```sql
create database mypages;
use mypages;
create table user
(
    id       bigint primary key auto_increment,
    username varchar(20),
    password varchar(40),
    nickname varchar(20),
    status   tinyint default 1
);
```

Assume there is a mapper defined like the following:

```java
public interface UserMapper {
    @Select("select * from user")
    List<User> query(@Param("page") Integer page, @Param("size") Integer size);
}
```

## 4.1. Java

```java
public void query() {
    DataSource dataSource = dataSource();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactory(dataSource);
    try (SqlSession session = sqlSessionFactory.openSession(false)) {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        List<User> list = userMapper.query(1, 10);
        Page<User> page = Page.of(list);
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
```

## 4.2. Spring

```xml
<bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
    <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/mypages?serverTimezone=GMT%2B8"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>

<bean id="paginationInterceptor" class="io.leego.mypages.interceptor.PaginationInterceptor">
    <constructor-arg name="settings">
        <bean class="io.leego.mypages.interceptor.PaginationSettings">
            <property name="database" value="MYSQL"/>
            <property name="pageField" value="page"/>
            <property name="sizeField" value="size"/>
        </bean>
    </constructor-arg>
</bean>

<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins" ref="paginationInterceptor"/>
</bean>
```

```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Page<User> query(int page, int size) {
        List<User> list = userMapper.query(page, size);
        return Page.of(list);
    }
}
```

## 4.3. Spring Boot

```java
@Configuration
public class MybatisConfiguration {
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
```

```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Page<User> query(int page, int size) {
        List<User> list = userMapper.query(page, size);
        return Page.of(list);
    }
}
```

## 4.4. Spring Boot Starter

> * Please see: [mypages-spring-boot-starter](../mypages-spring-boot-starter/README.md)

# 5. Enable Pagination

## 5.1. Annotations (Recommended)

Using `@Pagination`, `@Page`, `@Size`, `@Offset`, `@Rows`

```java
@Pagination
public class QueryParam {
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

More annotations: `@CountExpr`, `@CountMethodName`, `@DisableCount`, `@DisablePagination`

## 5.2. PaginationSettings (Recommended)

Obtaining paging parameters by reflection.

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL)
    .pageField("page")
    .sizeField("size")
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

### 5.2.1. Custom class

Assume there are classes defined like the following:

```java
public class QueryParam {
    private Integer page;
    private Integer size;

    public QueryParam(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
    /* getter setter */
}
```

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(QueryParam param);
}
```

### 5.2.2. MyBatis `@Param`

Assume there is a mapper defined like the following:

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(@Param("page") Integer page, @Param("size") Integer size);
}
```

### 5.2.3. `Map`

Assume there is a mapper defined like the following:

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(Map map);
}
```

Call the query method:

```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Page<User> query() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", 1);
        map.put("page", 10);
        return Page.of(userMapper.query(map));
    }
}
```

### Configurations

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL) // The database type such as `MySQL`, `PostgreSQL`, `Oracle`
    .countExpr("*") // The column name or expression
    .pageField("page") // The `page` field name of the parameter
    .sizeField("size") // The `size` field name of the parameter
    .offsetField("offset") // The `offset` field name of the parameter
    .rowsField("rows") // The `rows` field name of the parameter
    .countExprField("countExpr") // The count expression field name of the parameter
    .countMethodNameField("countMethodName") // The `count-method-name` field name of the parameter
    .enableCountField("enableCount") // The `enable-count` field name of the parameter
    .skipQueryIfCountEqualsZero(true) // Whether to skip query if total value equals zero
    .useGeneratedIfCountMethodIsMissing(true) // Whether to use generated if the specified count method is missing
    .useGracefulCountSql(true) // Whether to use graceful count-sql
    .keepSorting(false) // Whether to keep sorting
    .defaultPage(1) // Replaces the `page` with `default-page` if the `page` is `null` or less than `1`
    .defaultSize(10) // Replaces the `size` with `default-size` if the `size` is `null` or less than `1`
    .maxPage(10000) // Replaces the `page` with `max-page` if the `page` is greater than `max-page`
    .maxSize(10000) // Replaces the `size` with `max-size` if the `size` is greater than `max-size`
    .build();
```

## 5.3. Implements `io.leego.mypages.util.Pageable`

Define a class implements `io.leego.mypages.util.Pageable`.

```java
public class QueryParam implements io.leego.mypages.util.Pageable {
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

# 6. Using Graceful Count SQL

Please see：`io.leego.mypages.util.SqlUtils#toCountSql`

## 6.1. Simple

Input：
```sql
select * from user where status = ?
```
Output：
```sql
select count(*) from user where status = ?
```

## 6.2. Sorting

Input：
```sql
select * from user where status = ? order by username desc
```
Output：
```sql
select count(*) from user where status = ?
```

## 6.3. Keep Soring

Input：
```sql
select * from user where status = ? order by username desc
```
Output：
```sql
select count(*) from user where status = ? order by username desc
```

## 6.4. Aggregation

Input：
```sql
select nickname from user where status = ? group by nickname
```
Output：
```sql
select count(*) from (select nickname from user where status = ? group by nickname) mp_cta
```

## 6.5. With-As

Input：
```sql
with temp as (select * from user where status = ?)
select * from temp
```
Output：
```sql
with temp as (select * from user where status = ?)
select count(*) from temp
```

## 6.6. Sub-Query

Input：
```sql
select *
from (select * from user) u
where u.status = ?
```
Output：
```sql
select count(*)
from (select * from user) u
where u.status = ?
```

# 7. Using custom count methods

## 7.1. Annotations

```java
@Pagination
public class QueryParam {
    @Page
    private Integer page;
    @Size
    private Integer size;
    @CountMethodName
    private String countMethodName;

    public QueryParam(Integer page, Integer size, String countMethodName) {
        this.page = page;
        this.size = size;
        this.countMethodName = countMethodName;
    }
    /* getter setter */
}
```

## 7.2. Configurations

```java
public class QueryParam {
    private Integer page;
    private Integer size;
    private String countMethodName;

    public QueryParam(Integer page, Integer size, String countMethodName) {
        this.page = page;
        this.size = size;
        this.countMethodName = countMethodName;
    }
    /* getter setter */
}
```

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL)
    .pageField("page")
    .sizeField("size")
    .countMethodNameField("countMethodName")
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

## 7.3. Call `Query` and `Count` Methods

Assume there is a mapper defined like the following:

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(QueryParam param);
    
    @Select("SELECT COUNT(*) FROM user")
    long count(QueryParam param);
}
```

Specify the count method name:

```java
import io.leego.mypages.util.Page;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Page<User> query() {
        // Specifies the count method name.
        return Page.of(userMapper.query(new QueryParam(1, 10, "count")));
    }
}
```

# 8. Parameters rationalization

Rationalize the parameters if the values are invalid, the following parameters can be set:

**defaultPage**: Replaces the page with default-page if the page is null or less than 1.  
**defaultSize**: Replaces the size with default-size if the size is null or less than 1.  
**maxPage**: Replaces the page with max-page if the page is greater than max-page.  
**maxSize**: Replaces the size with max-size if the size is greater than max-size.

## 8.1. Annotations

```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 10000, maxSize = 10000)
public class QueryParam {
}
```

## 8.2. Configurations

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(10000)
    .maxSize(10000)
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

# 9. Query Results

## 9.1. Using `io.leego.mypages.util.Page`

#### 9.1.1. Wrap

```java
Page<Foo> page = Page.of(mapper.query(param));
```

#### 9.1.2. Convert

```java
Page<Bar> page = Page.of(mapper.query(param), foo -> new Bar(foo));
```

#### 9.1.3. Map

```java
Page<Bar> page = Page.of(mapper.query(param)).map(foo -> new Bar(foo));
```

## 9.2. Using  `io.leego.mypages.util.PaginationCollection`

```java
List<Foo> list = mapper.query(param);
PaginationCollection pc = (PaginationCollection) list;
long total = pc.getTotal();
int page = pc.getPage();
int size = pc.getSize();
```

# 10. Coding Specification

## 10.1. Using `org.apache.ibatis.annotations.Param`

Assume there are configurations defined like the following:

```java
PaginationInterceptor interceptor = new PaginationInterceptor(PaginationSettings.builder()
        .database(Database.MYSQL)
        .pageField("page")
        .sizeField("size")
        .sizeField("size")
        .offsetField("offset")
        .build());
```

```java
public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByStatusWithPageSizeParam(@Param("page") Integer page, @Param("size") Integer size, @Param("status") Integer status);

    @Select("select * from user where status = #{status}")
    List<User> findByStatusOffsetRowsWithParam(@Param("offset") Long offset, @Param("rows") Integer rows, @Param("status") Integer status);
}
```

## 10.2. Using `@Pagination`

### 10.2.1. Field declaration

```java
@Pagination
public class PaginationFieldParam {
    @Page
    private Integer page;
    @Size
    private Integer size;
    @Offset
    private Long offset;
    @Rows
    private Integer rows;
    private Integer status;
}

public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByPaginationField(PaginationFieldParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPaginationFieldWithParam0(@Param("param") PaginationFieldParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPaginationFieldWithParam1(@Param("param1") PaginationFieldParam param);
}
```

### 10.2.2. Method declaration

```java
@Pagination
public class PaginationMethodParam {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;

    @Page
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Size
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Offset
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Rows
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByPaginationMethod(PaginationMethodParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPaginationMethodWithParam0(@Param("param") PaginationMethodParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPaginationMethodWithParam1(@Param("param1") PaginationMethodParam param);
}
```

## 10.3. Using `io.leego.mypages.util.Pageable`

```java
public class PageableParam implements Pageable {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;
}

public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByPageable(PageableParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByPageableWithParam0(@Param("param") PageableParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByPageableWithParam1(@Param("param1") PageableParam param);
}
```

## 10.4. Using Custom Class

Assume there are configurations defined like the following:

```java
PaginationInterceptor interceptor = new PaginationInterceptor(PaginationSettings.builder()
        .database(Database.MYSQL)
        .pageField("page")
        .sizeField("size")
        .sizeField("size")
        .offsetField("offset")
        .build());
```

```java
public class CustomClassParam {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;
}

public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByCustomClass(CustomClassParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByCustomClassWithParam0(@Param("param") CustomClassParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCustomClassWithParam1(@Param("param1") CustomClassParam param);
}
```

## 10.5. Using `Map`

Assume there are configurations defined like the following:

```java
PaginationInterceptor interceptor = new PaginationInterceptor(PaginationSettings.builder()
        .database(Database.MYSQL)
        .pageField("page")
        .sizeField("size")
        .sizeField("size")
        .offsetField("offset")
        .build());
```

## 10.5.1. Using `java.util.Map`

```java
public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByCollectionMap(Map param);

    @Select("select * from user where status = #{map.status}")
    List<User> findByCollectionMapWithParam0(@Param("map") Map map);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCollectionMapWithParam1(@Param("param1") Map map);
}
```

## 10.5.2. Using Custom `Map`

```java
public class CustomMapParam extends HashMap<String, Object> {
}

public interface UserMapper  {
    @Select("select * from user where status = #{status}")
    List<User> findByCustomMap(CustomMapParam param);

    @Select("select * from user where status = #{param.status}")
    List<User> findByCustomMapWithParam0(@Param("param") CustomMapParam param);

    @Select("select * from user where status = #{param1.status}")
    List<User> findByCustomMapWithParam1(@Param("param1") CustomMapParam param);
}
```