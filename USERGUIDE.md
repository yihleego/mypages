# Getting Started

Please make sure that Java version is 1.8 and above.


# Installation

### Maven Dependency

```xml
<properties>
    <mypages.version>0.0.2</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

### Gradle Dependency

```xml
implementation 'io.leego:mypages:0.0.2'
```


# Quick Setup

To use MyBatis with Spring you need at least two things defined in the Spring application context: an SqlSessionFactory and at least one mapper interface.

In MyBatis-Spring, an SqlSessionFactoryBean is used to create an SqlSessionFactory. To configure the factory bean, put the following in the Spring configuration file:

### Spring XML

```xml
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins">
        <bean class="io.leego.mypages.interceptor.PaginationInterceptor">
            <property name="sqlDialect" value="MYSQL"/>
            <property name="pageFieldName" value="page"/>
            <property name="sizeFieldName" value="size"/>
        </bean>
    </property>
</bean>

<bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.example.mapper"/>
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
</bean>
```

### Spring Boot

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
        return new PaginationInterceptor()
                .sqlDialect(SqlDialect.POSTGRESQL)
                .pagingFields("page", "size");
    }
}
```

Notice that the PaginationInterceptor requires SqlDialect. This can be any SqlDialect and should be configured.

Assume you have a search class defined like the following:

```java
public class UserSearch {
    private String name;
    private Integer page;
    private Integer size;
    /* getter setter */
}
```

Assume you have a mapper interface defined like the following:

```java
public interface UserMapper {
    @Select("SELECT * FROM user WHERE name LIKE CONCAT(#{name},'%')")
    List<User> listUser(UserSearch search);
}
```

Calling paging methods is now only a few lines of code:

```java
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserMapper userMapper;

    public Page<User> searchUser(UserSearch search) {
        return Page.of(userMapper.listUser(search));
    }
}
```


# Enable Pagination

### Extends Search

Defined a class extends ```io.leego.mypages.util.Search```

```java
public class SearchDTO extends io.leego.mypages.util.Search {
    private String name;
    /* getter setter */
}
```

### Annotation

Using ```@Pagination```, ```@Page```, ```@Size```, ```@Offset```, ```@Rows``` annotations.

```java
@Pagination
public class SearchDTO {
    private String name;
    @Page
    private Integer page;
    @Size
    private Integer size;
    /* getter setter */
}
```

Or

```java
@Pagination
public class SearchDTO {
    private String name;
    @Offset
    private Integer offset;
    @Rows
    private Integer rows;
    /* getter setter */
}
```

### Get Parameters From Fields

Defined a class with paging parameters, configure parameters field names.

```java
public class SearchDTO {
    private String name;
    private Integer page;
    private Integer size;
    /* getter setter */
}
```

```java
new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .pagingFields("page","size");
```

Or

```java
public class SearchDTO {
    private String name;
    private Integer offset;
    private Integer rows;
    /* getter setter */
}
```

```java
new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .offsetRowsFields("offset","rows");
```


# Reasonable

Rationalize parameters if reasonable is enabled, the following parameters can be set:

**defaultPage**: Using default-page instead of page, if page number is null or less than 1, the default is 1.

**defaultSize**: Using default-size instead of size, if page size is null or less than 1, the default is 20.

**maxPage**: Using max-page instead of page, if page number is greater than this, the default is 10000.

**maxSize**: Using max-size instead of size, if page size is greater than this, the default is 10000.


These can be set up like the following:

```java
@Pagination(defaultPage = 1, defaultSize = 10, maxPage = 100, maxSize = 100, reasonable = true)
public class SearchDTO {}
```

Or

```java
new PaginationInterceptor()
    .sqlDialect(SqlDialect.MYSQL)
    .reasonable(true)
    .defaultPage(1)
    .defaultSize(10)
    .maxPage(100)
    .maxSize(100);
```


# Query Results

If the paging query proceed, it will return an instance of the ```PaginationCollection```.

Assume you have a mapper interface defined like the following:

```java
public interface UserMapper {
    @Select("SELECT * FROM user WHERE name LIKE CONCAT(#{name},'%')")
    List<User> listUser(UserSearch search);
}
```

### Ready-Made Wrapper Class

Using inner paging query result wrapper class: 
```io.leego.mypages.util.Page```

```java
Page<User> result = Page.of(userMapper.listUser(search));
```

### Custom Class & Util

For example:

```java
public class PageResult<T> {
    private List<T> list;
    private Integer page;
    private Integer size;
    private Long total;
    private Long totalPages;
    private Boolean next;
    private Boolean previous;

    public PageResult(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
        this.next = next;
        this.previous = previous;
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
            return of(list, total);
        }
        boolean next = page * size < total;
        boolean previous = page > 1;
        long totalPages = total % size > 0 ? total / size + 1 : total / size;
        return new PageResult<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> PageResult<T> of(List<T> list, Long total) {
        return new PageResult<>(list, total);
    }

    public static <T> PageResult<T> of(Collection<T> collection) {
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
            return Collections.emptyList();
        }
        return new ArrayList<>(collection);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(Collections.emptyList(), 0, 0, 0L, 0L, false, false);
    }
    
    /*  getter setter */

}
```

```java
public final class PageUtils {
    private PageUtils() {
    }

    public static <T> PageResult<T> of(Collection<T> collection) {
        if (collection instanceof PaginationCollection) {
            if (collection instanceof PaginationList) {
                return of((PaginationList<T>) collection);
            } else if (collection instanceof PaginationSet) {
                return of((PaginationSet<T>) collection);
            } else if (collection instanceof PaginationQueue) {
                return of((PaginationQueue<T>) collection);
            }
        } else if (collection instanceof List) {
            return new PageResult<>((List<T>) collection);
        }
        return new PageResult<>(toList(collection));
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

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(collection);
    }
}
```

```java
PageResult<User> result = PageUtils.of(userMapper.listUser(search));
```
