# Getting Started

Please make sure that Java version is 1.8 and above.

# Installation

## Maven Dependency

```xml
<properties>
    <mypages.version>0.3.0</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

## Gradle Dependency

```xml
implementation 'io.leego:mypages:0.3.0'
```

# Quick Setup

To use MyBatis with Spring you need at least two things defined in the Spring application context: an SqlSessionFactory and at least one mapper interface.

In MyBatis-Spring, an SqlSessionFactoryBean is used to create an SqlSessionFactory. To configure the factory bean, put the following in the Spring configuration file:

## Spring XML

```xml
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins">
        <!-- Configures pagination plugin -->
        <bean class="io.leego.mypages.interceptor.PaginationInterceptor">
            <property name="sqlDialect" value="MYSQL"/>
        </bean>
    </property>
</bean>
```

## Spring Boot

```java
@Configuration
@MapperScan(basePackages = "com.example.mapper")
public class MybatisConfig {
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
        return new PaginationInterceptor(SqlDialect.MYSQL);
    }
}
```

Notice that the PaginationInterceptor requires SqlDialect. This can be any SqlDialect and should be configured.

Assume you have a search class defined like the following:

```java
import io.leego.mypages.annotation.Pagination;
import io.leego.mypages.annotation.Page;
import io.leego.mypages.annotation.Size;

@Pagination
public class SearchDTO {
    @Page
    private Integer page;
    @Size
    private Integer size;
    /* getter setter */
}
```

Assume you have a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> list(SearchDTO search);
}
```

Calling paging methods is now only a few lines of code:

```java
import io.leego.mypages.util.Page;

public class FooServiceImpl implements FooService {
    @Autowired
    private final FooMapper fooMapper;

    public Page<Foo> search(SearchDTO search) {
        return Page.of(fooMapper.list(search));
    }
}
```

# Enable Pagination

## 1.Annotations (Recommended)

Use ```@Pagination```, ```@Page```, ```@Size``` annotations.

```java
@Pagination
public class SearchDTO {
    @Page
    private Integer page;
    @Size
    private Integer size;
    /* getter setter */
}
```

Or use ```@Pagination```, ```@Offset```, ```@Rows``` annotations.

```java
@Pagination
public class SearchDTO {
    @Offset
    private Integer offset;
    @Rows
    private Integer rows;
    /* getter setter */
}
```

More annotations: ```@CountColumn```, ```@DisableCount```, ```@DisablePagination```.

## 2.Configure PaginationInterceptor (Recommended)

Define a class with paging parameters, and configure parameters field names.

```java
public class SearchDTO {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer rows;
    private String column;
    private boolean allowCount;
    /* getter setter */
}
```

```java
// Obtains page and size values from fields of parameter.
PaginationInterceptor pagingPlugin = new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .pagingFields("page", "size");
```

```java
// Obtains offset and rows values from fields of parameter.
PaginationInterceptor offsetRowsPlugin = new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .offsetRowsFields("offset", "rows");
```

Others: 

```java
PaginationInterceptor plugin = new PaginationInterceptor()
    // SqlDialect
    .sqlDialect(SqlDialect.MYSQL)
    // Counting column name, the default value is "*".
    .countColumn("*")
    // The field name of counting column name.
    .countColumnFieldName("column")
    // whether to count by value of filed.
    .enableCountFieldName("allowCount")
    // Whether to skip query if total value equals zero.
    .skipQueryIfCountEqualsZero(true)
    // Replaces page with defaultPage if page is invalid.
    .defaultPage(1)
    // Replaces size with defaultSize if size is invalid.
    .defaultSize(20)
    // Replaces page with maxPage if page is invalid.
    .maxPage(9999)
    // Replaces size with maxSize if size is invalid.
    .maxSize(1000);
```

## 3.Extends ```io.leego.mypages.util.Search```

Define a class extends ```io.leego.mypages.util.Search```.

```java
public class SearchDTO extends io.leego.mypages.util.Search {
    private String name;
    /* getter setter */
}
```

# Reasonable

Rationalize parameters if the value is invalid, the following parameters can be set:

**defaultPage**: Replaces page with defaultPage if page number is null or less than 1.

**defaultSize**: Replaces size with defaultSize if page size is null or less than 1.

**maxPage**: Replaces page with maxPage if page number is greater than maxPage.

**maxSize**: Replaces size with maxSize if page size is greater than maxSize.


These can be set up like the following:

```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 100, maxSize = 100)
public class SearchDTO {}
```

Or

```java
new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(100)
    .maxSize(100);
```

# Query Results

If the paging query proceed, it will return an instance of the ```PaginationCollection```.

Assume you have a mapper interface defined like the following:

```java
public interface FooMapper {
    @Select("SELECT * FROM foo")
    List<Foo> list(SearchDTO search);
}
```

## 1.Ready-Made Wrapper Class

Use paging query result wrapper class: ```io.leego.mypages.util.Page```.

```java
Page<Foo> result = Page.of(fooMapper.list(search));
```

```java
Page<Bar> result = Page.of(fooMapper.list(search), foo -> new Bar(foo.getName()));
```

## 2.Custom Classes & Utils

For example:

```java
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 3214571808482585491L;
    protected List<T> list;
    protected Integer page;
    protected Integer size;
    protected Long total;
    protected Long totalPages;
    protected Boolean next;
    protected Boolean previous;

    public PageResult(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
        this.next = next;
        this.previous = previous;
    }

    public PageResult(List<T> list, Integer page, Integer size) {
        this.list = list;
        this.page = page;
        this.size = size;
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(List<T> list) {
        this.list = list;
    }

    public static <T> PageResult<T> of(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        return new PageResult<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> PageResult<T> of(List<T> list, Integer page, Integer size, Long total) {
        if (page == null || size == null) {
            return new PageResult<>(list, total);
        }
        if (total == null) {
            return new PageResult<>(list, page, size);
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
        return new PageResult<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> PageResult<T> of(List<T> list, Integer page, Integer size) {
        return new PageResult<>(list, page, size);
    }

    public static <T> PageResult<T> of(List<T> list, Long total) {
        return new PageResult<>(list, total);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(new ArrayList<>(), 0, 0, 0L, 0L, false, false);
    }

    public static <T> PageResult<T> of(Collection<T> collection) {
        if (collection instanceof List) {
            return new PageResult<>((List<T>) collection);
        }
        return new PageResult<>(toList(collection));
    }

    public static <T> PageResult<T> of(List<T> list) {
        return new PageResult<>(list);
    }

    public static <T> PageResult<T> of(Set<T> set) {
        return new PageResult<>(toList(set));
    }

    public static <T> PageResult<T> of(Queue<T> queue) {
        return new PageResult<>(toList(queue));
    }

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(collection);
    }

    /* getter setter */

}
```

```java
public final class PageUtils {
    private PageUtils() {
    }

    public static <T> PageResult<T> of(PaginationList<T> list) {
        if (list == null) {
            return PageResult.empty();
        }
        return PageResult.of(list, list.getPage(), list.getSize(), list.getTotal());
    }

    public static <T> PageResult<T> of(PaginationSet<T> set) {
        if (set == null) {
            return PageResult.empty();
        }
        return PageResult.of(toList(set), set.getPage(), set.getSize(), set.getTotal());
    }

    public static <T> PageResult<T> of(PaginationQueue<T> queue) {
        if (queue == null) {
            return PageResult.empty();
        }
        return PageResult.of(toList(queue), queue.getPage(), queue.getSize(), queue.getTotal());
    }

    public static <T> PageResult<T> of(Collection<T> collection) {
        if (collection instanceof PaginationList) {
            return of((PaginationList<T>) collection);
        } else if (collection instanceof PaginationSet) {
            return of((PaginationSet<T>) collection);
        } else if (collection instanceof PaginationQueue) {
            return of((PaginationQueue<T>) collection);
        } else if (collection instanceof List) {
            return new PageResult<>((List<T>) collection);
        }
        return new PageResult<>(toList(collection));
    }

    public static <T> PageResult<T> of(List<T> list) {
        if (list instanceof PaginationList) {
            return of((PaginationList<T>) list);
        }
        return new PageResult<>(list);
    }

    public static <T> PageResult<T> of(Set<T> set) {
        if (set instanceof PaginationSet) {
            return of((PaginationSet<T>) set);
        }
        return new PageResult<>(toList(set));
    }

    public static <T> PageResult<T> of(Queue<T> queue) {
        if (queue instanceof PaginationQueue) {
            return of((PaginationQueue<T>) queue);
        }
        return new PageResult<>(toList(queue));
    }


    public static <T, S> PageResult<T> of(PaginationList<S> list, Function<? super S, ? extends T> mapper) {
        if (list == null) {
            return PageResult.empty();
        }
        return PageResult.of(mapping(list, mapper), list.getPage(), list.getSize(), list.getTotal());
    }

    public static <T, S> PageResult<T> of(PaginationSet<S> set, Function<? super S, ? extends T> mapper) {
        if (set == null) {
            return PageResult.empty();
        }
        return PageResult.of(mapping(set, mapper), set.getPage(), set.getSize(), set.getTotal());
    }

    public static <T, S> PageResult<T> of(PaginationQueue<S> queue, Function<? super S, ? extends T> mapper) {
        if (queue == null) {
            return PageResult.empty();
        }
        return PageResult.of(mapping(queue, mapper), queue.getPage(), queue.getSize(), queue.getTotal());
    }

    public static <T, S> PageResult<T> of(Collection<S> collection, Function<? super S, ? extends T> mapper) {
        if (collection instanceof PaginationList) {
            return of((PaginationList<S>) collection, mapper);
        } else if (collection instanceof PaginationSet) {
            return of((PaginationSet<S>) collection, mapper);
        } else if (collection instanceof PaginationQueue) {
            return of((PaginationQueue<S>) collection, mapper);
        }
        return new PageResult<>(mapping(collection, mapper));
    }

    public static <T, S> PageResult<T> of(List<S> list, Function<? super S, ? extends T> mapper) {
        if (list instanceof PaginationList) {
            return of((PaginationList<S>) list, mapper);
        }
        return new PageResult<>(mapping(list, mapper));
    }

    public static <T, S> PageResult<T> of(Set<S> set, Function<? super S, ? extends T> mapper) {
        if (set instanceof PaginationSet) {
            return of((PaginationSet<S>) set, mapper);
        }
        return new PageResult<>(mapping(set, mapper));
    }

    public static <T, S> PageResult<T> of(Queue<S> queue, Function<? super S, ? extends T> mapper) {
        if (queue instanceof PaginationQueue) {
            return of((PaginationQueue<S>) queue, mapper);
        }
        return new PageResult<>(mapping(queue, mapper));
    }


    private static <T, S> List<T> mapping(Collection<S> source, Function<? super S, ? extends T> mapper) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(collection);
    }

}
```

```java
PageResult<Foo> result = PageUtils.of(fooMapper.list(search));
```
