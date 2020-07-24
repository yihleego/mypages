# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 and above.

# 3. Dependency

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

# 4. Quick Setup

Notice that the PaginationInterceptor requires SqlDialect. It can be any SqlDialect and must be configured.

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

To use MyBatis with Spring you need at least two things defined in the Spring application context: an SqlSessionFactory and at least one mapper interface.

In MyBatis-Spring, an SqlSessionFactoryBean is used to create an SqlSessionFactory. To configure the factory bean, put the following in the Spring configuration file:

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

> * Please see: [mypages-spring-boot-starter](https://github.com/yihleego/mypages-spring-boot-starter)

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

## Annotations (Recommended)

Using ```@Pagination```, ```@Page```, ```@Size```, ```@Offset```, ```@Rows``` annotations.

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

More annotations: ```@CountColumn```, ```@CountMethodName```, ```@DisableCount```, ```@DisablePagination```.

## PaginationInterceptor Configuration (Recommended)

Define a class with paging parameters, and configure parameters field names.

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

### Obtains the page and size values from fields

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .pagingFields("page", "size");
```

### Obtains the offset and rows values from fields

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .offsetRowsFields("offset", "rows");
```

### More configurations.

```java
PaginationInterceptor interceptor = new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL) // Specifies sql dialect.
    .pagingFields("page", "size") // Obtains the page and size values from fields.
    .offsetRowsFields("offset", "rows") // Obtains the offset and rows values from fields.
    .countColumn("*") // The count column name, the default value is "*".
    .countColumnFieldName("column") // Obtains the count column name from fields.
    .enableCountFieldName("allowCount") // Whether to enable count.
    .skipQueryIfCountEqualsZero(true) // Whether to skip query if total quantity equals zero.
    .defaultPage(1) // Replaces the page with default-page if the page is null or less than 1.
    .defaultSize(20) // Replaces the size with default-size if the size is null or less than 1.
    .maxPage(9999) // Replaces the page with max-page if the page is greater than max-page.
    .maxSize(1000); // Replaces the size with max-size if the size is greater than max-size.
    
```

## Extends ```io.leego.mypages.util.Pageable```

Define a class extends ```io.leego.mypages.util.Pageable```.

```java
public class PageableDTO extends io.leego.mypages.util.Pageable {
    private String name;
    /* getter setter */
}
```

# 7. Using custom count methods

## Annotations

Assume there is a class defined like the following:

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

## PaginationInterceptor Configuration

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

## Call query and count methods:

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

These can be set up like the following:

## Annotations
```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 1000, maxSize = 1000)
public class Pageable {}
```

## PaginationInterceptor Configuration

```java
PaginationInterceptor interceptor = new PaginationInterceptor(SqlDialect.MYSQL)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(1000)
    .maxSize(1000);
```

# 9. Query Results

If the invocation proceed, it will return an instance of the ```PaginationCollection```.

## Using Wrapper Class ```io.leego.mypages.util.Page``` 

#### Wrap

```java
Page<Foo> result = Page.of(fooMapper.query(pageable));
```

#### Convert

```java
Page<Bar> result = Page.of(fooMapper.query(pageable), foo -> new Bar(foo));
```

#### Map

```java
Page<Bar> result = Page.of(fooMapper.query(pageable))
    .map(foo -> new Bar(foo));
```

## Custom Classes & Utils

For example:

#### Wrapper Class

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

#### Util

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

Finally, use the custom wrapper class and util.

```java
Pagination<Foo> result = PageUtils.of(fooMapper.query(pageable));
```

```java
Pagination<Bar> result = PageUtils.of(fooMapper.query(pageable), foo -> new Bar(foo));
```