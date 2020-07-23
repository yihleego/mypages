# 1. 简介

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

# 2. 准备工作

请确保您的Java版本在1.8及以上。

# 3. 依赖

## Maven

```xml
<properties>
    <mypages.version>0.4.0</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

## Gradle

```xml
implementation 'io.leego:mypages:0.4.0'
```

# 4. 快速设置

请注意，PaginationInterceptor需要配置SqlDialect，它可以是任何SqlDialect且必须被配置。

## MyBatis SqlSessionFactoryBean

```java
// Plugins
PaginationInterceptor paginationInterceptor = new PaginationInterceptor(SqlDialect.MYSQL);
Interceptor[] plugins = new Interceptor[]{paginationInterceptor};
// SqlSessionFactoryBean
SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(plugins);
```

## Spring

要在Spring中使用MyBatis，至少需要在Spring应用程序上下文中定义两个东西：SqlSessionFactory对象和至少一个mapper接口。

在MyBatis-Spring中，SqlSessionFactoryBean用于创建SqlSessionFactory。要配置工厂bean，请在Spring配置文件中放入以下内容：

### Spring XML

```xml
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins">
        <bean class="io.leego.mypages.interceptor.PaginationInterceptor">
            <property name="sqlDialect" value="MYSQL"/>
        </bean>
    </property>
</bean>
```

### Spring Boot

```java
@Configuration
public class MybatisConfiguration {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // Plugins
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor(SqlDialect.MYSQL);
        Interceptor[] plugins = new Interceptor[]{paginationInterceptor};
        // SqlSessionFactoryBean
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor(SqlDialect.MYSQL);
    }
}
```

### Spring Boot Starter

> * 请使用: [mypages-spring-boot-starter](https://github.com/yihleego/mypages-spring-boot-starter)

# 5. 快速开始

假设存在如下定义的类：

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

假设存在如下定义的Mapper：

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Pageable pageable);
}
```

调用查询方法：

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

# 6. 启用分页

## 注解 (推荐)

使用 ```@Pagination```, ```@Page```, ```@Size```, ```@Offset```, ```@Rows``` 注解.

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

更多注解: ```@CountColumn```, ```@CountMethodName```, ```@DisableCount```, ```@DisablePagination```.

## PaginationInterceptor配置 (推荐)

使用分页参数定义一个类，并配置参数字段名。

```java
public class Pageable {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer rows;
    private String column;
    private boolean allowCount;
    /* getter setter */
}
```

### 从参数字段中获取page和size值

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .pagingFields("page", "size");
```

### 从参数字段中获取offset和rows值

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .offsetRowsFields("offset", "rows");
```

### 更多配置

```java
PaginationInterceptor interceptor = new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL) // 指定SqlDialect。
    .pagingFields("page", "size") // 从参数字段中获取page和size值。
    .offsetRowsFields("offset", "rows") // 从参数字段中获取offset和rows值。
    .countColumn("*") // 设置count字段名称，默认值为"*"。
    .countColumnFieldName("column") // 从参数字段中获取count字段名称。
    .enableCountFieldName("allowCount") // 是否启用count。
    .skipQueryIfCountEqualsZero(true) // 是否跳过count如果总数量为0。
    .defaultPage(1) // 如果page为空或小于1，则用default-page替换page。
    .defaultSize(20) // 如果size为空或小于1，则用default-size替换size。
    .maxPage(9999) // 如果page大于max-page，则用max-page替换page。
    .maxSize(1000); // 如果size大于max-size，则用max-size替换size。
    
```

## 继承 ```io.leego.mypages.util.Pageable```

定义一个继承 ```io.leego.mypages.util.Pageable```的类.

```java
public class PageableDTO extends io.leego.mypages.util.Pageable {
    private String name;
    /* getter setter */
}
```

# 7. 使用自定义Count方法

## 注解

假设存在如下定义的类：

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

## PaginationInterceptor配置

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
PaginationInterceptor pagingPlugin = new PaginationInterceptor(SqlDialect.MYSQL)
    .pagingFields("page", "size")
    .specifyCountMethod("countMethodName");
```

## 调用Query和Count方法

假设存在如下定义的Mapper：

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> query(Pageable pageable);
    
    @Select("SELECT COUNT(*) FROM foo")
    long count(Pageable pageable);
}
```

指定Count方法名称：

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

# 8. 参数合理化
  
如果分页参数无效，需要合理化参数，可以设置以下参数:

**defaultPage**: 如果page为空或小于1，则用default-page替换page。
**defaultSize**: 如果size为空或小于1，则用default-size替换size。
**maxPage**: 如果page大于max-page，则用max-page替换page。
**maxSize**: 如果size大于max-size，则用max-size替换size。

可以按如下方式进行配置：

## 注解配置
```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 1000, maxSize = 1000)
public class Pageable {}
```

## PaginationInterceptor配置

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(1000)
    .maxSize(1000);
```

# 9. 查询结果

如果调用执行成功结束，它将返回一个```PaginationCollection```实例。

## 使用包装 ```io.leego.mypages.util.Page``` 

#### 包装

```java
Page<Foo> result = Page.of(fooMapper.query(pageable));
```

#### 转化

```java
Page<Bar> result = Page.of(fooMapper.query(pageable), foo -> new Bar(foo));
```

#### 映射

```java
Page<Bar> result = Page.of(fooMapper.query(pageable))
    .map(foo -> new Bar(foo));
```

## 自定义包装类和工具类

参考例子：

#### 包装类

```java
public class Pagination<T> implements Serializable {
    protected List<T> list;
    protected Integer page;
    protected Integer size;
    protected Long total;
    protected Long totalPages;
    protected Boolean next;
    protected Boolean previous;
    protected Object extra;

    public Pagination(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous, Object extra) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
        this.next = next;
        this.previous = previous;
        this.extra = extra;
    }

    public Pagination(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
        this.next = next;
        this.previous = previous;
    }

    public Pagination(List<T> list, Integer page, Integer size) {
        this.list = list;
        this.page = page;
        this.size = size;
    }

    public Pagination(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public Pagination(List<T> list) {
        this.list = list;
    }

    public static <T> Pagination<T> of(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous, Object extra) {
        return new Pagination<>(list, page, size, total, totalPages, next, previous, extra);
    }

    public static <T> Pagination<T> of(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        return new Pagination<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> Pagination<T> of(List<T> list, Integer page, Integer size, Long total) {
        if (page == null || size == null) {
            return new Pagination<>(list, total);
        }
        if (total == null) {
            return new Pagination<>(list, page, size);
        }
        boolean next;
        boolean previous;
        long totalPages;
        if (page > 0 && size > 0) {
            next = page * size < total;
            previous = page != 1;
            totalPages = total % size > 0 ? total / size + 1 : total / size;
        } else {
            next = false;
            previous = false;
            totalPages = 0L;
        }
        return new Pagination<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> Pagination<T> of(List<T> list, Integer page, Integer size) {
        return new Pagination<>(list, page, size);
    }

    public static <T> Pagination<T> of(List<T> list, Long total) {
        return new Pagination<>(list, total);
    }

    public static <T> Pagination<T> of(List<T> list) {
        return new Pagination<>(list);
    }

    public static <T> Pagination<T> of(Collection<T> collection) {
        if (collection == null) {
            return new Pagination<>(new ArrayList<>());
        }
        if (collection instanceof List) {
            return new Pagination<>((List<T>) collection);
        }
        return new Pagination<>(new ArrayList<>(collection));
    }

    public static <T> Pagination<T> empty() {
        return new Pagination<>(new ArrayList<>());
    }

    public <U> Pagination<U> map(Function<? super T, ? extends U> converter) {
        return new Pagination<>(list == null ? null : list.stream().map(converter).collect(Collectors.toList()),
                page, size, total, totalPages, next, previous, extra);
    }

    public int getCurrent() {
        return list != null ? list.size() : 0;
    }

    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    /* getter setter */

}
```

#### 工具类

```java
public final class PageUtils {
    private PageUtils() {}

    public static <T> Pagination<T> of(List<T> source) {
        if (source == null) {
            return Pagination.empty();
        }
        if (!(source instanceof PaginationList)) {
            return Pagination.of(source);
        }
        PaginationList<T> s = (PaginationList<T>) source;
        return Pagination.of(source, s.getPage(), s.getSize(), s.getTotal());
    }

    public static <T> Pagination<T> of(Collection<T> source) {
        if (source == null) {
            return Pagination.empty();
        }
        if (!(source instanceof PaginationCollection)) {
            return Pagination.of(source);
        }
        PaginationCollection<T> s = (PaginationCollection<T>) source;
        return Pagination.of(toList(source), s.getPage(), s.getSize(), s.getTotal());
    }

    public static <T, S> Pagination<T> of(Collection<S> source, Function<? super S, ? extends T> mapper) {
        if (source == null) {
            return Pagination.empty();
        }
        if (!(source instanceof PaginationCollection)) {
            return Pagination.of(mapping(source, mapper));
        }
        PaginationCollection<S> s = (PaginationCollection<S>) source;
        return Pagination.of(mapping(source, mapper), s.getPage(), s.getSize(), s.getTotal());
    }

    public static <T, S> Pagination<T> transfer(Collection<S> source, Collection<T> target) {
        if (source == null || target == null) {
            return Pagination.empty();
        }
        if (!(source instanceof PaginationCollection)) {
            return Pagination.of(toList(target));
        }
        PaginationCollection<S> s = (PaginationCollection<S>) source;
        return Pagination.of(toList(target), s.getPage(), s.getSize(), s.getTotal());
    }

    private static <T, S> List<T> mapping(Collection<S> source, Function<? super S, ? extends T> mapper) {
        Objects.requireNonNull(mapper);
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        if (collection instanceof List) {
            return (List<T>) collection;
        }
        return new ArrayList<>(collection);
    }

}
```

使用自定义包装类和工具类：

```java
Pagination<Foo> result = PageUtils.of(fooMapper.query(pageable));
```

```java
Pagination<Bar> result = PageUtils.of(fooMapper.query(pageable), foo -> new Bar(foo));
```