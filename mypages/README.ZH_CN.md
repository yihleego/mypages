# 1. 简介

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

# 2. 准备工作

请确保您的Java版本在1.8及以上。

# 3. Maven 依赖

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

# 4. 快速开始

假设存在基于`MySQL`的数据库和表：

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

假设存在如下定义的Mapper：

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

> * 请使用：[mypages-spring-boot-starter](../mypages-spring-boot-starter/README.ZH_CN.md)

# 5. 启用分页

## 5.1. 注解 (推荐)

使用 `@Pagination`、`@Page`、`@Size`、`@Offset`、`@Rows` 等注解.

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

更多注解: `@CountExpr`、`@CountMethodName`、`@DisableCount`、`@DisablePagination`

## 5.2. PaginationInterceptor配置 (推荐)

通过反射获取分页参数

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL)
    .pageField("page")
    .sizeField("size")
    .build();
PaginationInterceptor interceptor = new PaginationInterceptor(settings);
```

### 5.2.1. 定义类

假设存在如下定义的类和Mapper：

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

假设存在如下定义的Mapper：

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(@Param("page") Integer page, @Param("size") Integer size);
}
```

### 5.2.3. `Map`

假设存在如下定义的Mapper：

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(Map map);
}
```

调用查询方法：

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

### 更多配置

```java
PaginationSettings settings = PaginationSettings.builder()
    .database(Database.MYSQL) // 指定数据库类型，如：`MySQL`、`PostgreSQL`、`Oracle`等
    .countExpr("*") // 设置count表达式，默认值为`*`
    .pageField("page") // 从参数字段中获取`page`值
    .sizeField("size") // 从参数字段中获取`size`值
    .offsetField("offset") // 从参数字段中获取`offset`值
    .rowsField("rows") // 从参数字段中获取`rows`值
    .countExprField("countExpr") // 从参数字段中获取count表达式
    .countMethodNameField("countMethodName") // 从参数字段中获取自定义count方法名称
    .enableCountField("enableCount") // 从参数字段中判断是否启用count
    .skipQueryIfCountEqualsZero(true) // 是否跳过查询如果结果数量为`0`
    .useGeneratedIfCountMethodIsMissing(true) // 是否使用自动生成的count方法如果指定count方法不存在
    .useGracefulCountSql(true) // 是否使用优雅的Count-SQL
    .keepSorting(false) // 是否保留排序
    .defaultPage(1) // 如果`page`为空或小于`1`，则用`default-page`替换`page`
    .defaultSize(10) // 如果`size`为空或小于`1`，则用`default-size`替换`size`
    .maxPage(10000) // 如果`page`大于`max-page`，则用`max-page`替换`page`
    .maxSize(10000) // 如果`size`大于`max-size`，则用`max-size`替换`size`
    .build();
```

## 5.3. 使用 `io.leego.mypages.util.Pageable`接口

定义一个实现 `io.leego.mypages.util.Pageable`接口的类.

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

# 6. 使用优雅Count SQL

详情请看：`io.leego.mypages.util.SqlUtils#toCountSql`

## 6.1. 简单查询

输入：
```sql
select * from user where status = ?
```
输出：
```sql
select count(*) from user where status = ?
```

## 6.2. 排序查询

输入：
```sql
select * from user where status = ? order by username desc
```
输出：
```sql
select count(*) from user where status = ?
```

## 6.3. 保留排序

输入：
```sql
select * from user where status = ? order by username desc
```
输出：
```sql
select count(*) from user where status = ? order by username desc
```

## 6.4. 聚合查询

输入：
```sql
select nickname from user where status = ? group by nickname
```
输出：
```sql
select count(*) from (select nickname from user where status = ? group by nickname) mp_cta
```

## 6.5. With-As

输入：
```sql
with temp as (select * from user where status = ?)
select * from temp
```
输出：
```sql
with temp as (select * from user where status = ?)
select count(*) from temp
```

## 6.6. 子查询

输入：
```sql
select *
from (select * from user) u
where u.status = ?
```
输出：
```sql
select count(*)
from (select * from user) u
where u.status = ?
```

# 7. 使用自定义Count方法

## 7.1. 注解

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

## 7.2. PaginationInterceptor配置

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

## 7.3. 调用Query和Count方法

假设存在如下定义的Mapper：

```java
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> query(QueryParam param);
    
    @Select("SELECT COUNT(*) FROM user")
    long count(QueryParam param);
}
```

指定Count方法名称：

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

# 8. 参数合理化

如果分页参数无效，需要合理化参数，可以设置以下参数:

**defaultPage**: 如果page为空或小于1，则用default-page替换page。  
**defaultSize**: 如果size为空或小于1，则用default-size替换size。  
**maxPage**: 如果page大于max-page，则用max-page替换page。  
**maxSize**: 如果size大于max-size，则用max-size替换size。  

## 8.1. 注解配置

```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 10000, maxSize = 10000)
public class QueryParam {
}
```

## 8.2. PaginationInterceptor配置

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

# 9. 查询结果

## 9.1. 使用 `io.leego.mypages.util.Page`

#### 9.1.1. 包装

```java
Page<Foo> page = Page.of(mapper.query(param));
```

#### 9.1.2. 转化

```java
Page<Bar> page = Page.of(mapper.query(param), foo -> new Bar(foo));
```

#### 9.1.3. 映射

```java
Page<Bar> page = Page.of(mapper.query(param)).map(foo -> new Bar(foo));
```

## 9.2. 使用 `io.leego.mypages.util.PaginationCollection`

```java
List<Foo> list = mapper.query(param);
PaginationCollection pc = (PaginationCollection) list;
long total = pc.getTotal();
int page = pc.getPage();
int size = pc.getSize();
```

# 10. 分页编码规范

## 10.1. 使用`org.apache.ibatis.annotations.Param`注解

假设存在以下配置：

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

## 10.2. 使用`@Pagination`注解

### 10.2.1. Field注解

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

### 10.2.2. Method注解

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

## 10.3. 使用`io.leego.mypages.util.Pageable`接口

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

## 10.4. 使用自定义类

假设存在以下配置：

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

## 10.5. 使用`Map`类

假设存在以下配置：

```java
PaginationInterceptor interceptor = new PaginationInterceptor(PaginationSettings.builder()
        .database(Database.MYSQL)
        .pageField("page")
        .sizeField("size")
        .sizeField("size")
        .offsetField("offset")
        .build());
```

## 10.5.1. 使用`java.util.Map`类

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

## 10.5.2. 使用自定义`Map`类

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