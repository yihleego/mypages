# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 and above.

# 3. Dependency

## 3.1 Maven

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages-spring-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```

## 3.2 Gradle

```xml
implementation 'io.leego:mypages-spring-boot-starter:1.1.0'
```

# 4. Configuration

|Property|Description|
|:-|:-|
|spring.mypages.enabled|Whether to enable mypages.|
|spring.mypages.sql-dialect|The sql dialect such as `MySQL`, `PostgreSQL`, `Oracle`, etc.|
|spring.mypages.count-expr|The column name or expression, the default value is `*`.|
|spring.mypages.page-field|The `page` field name of the parameter.|
|spring.mypages.size-field|The `size` field name of the parameter.|
|spring.mypages.offset-field|The `offset` field name of the parameter.|
|spring.mypages.rows-field|The `rows` field name of the parameter.|
|spring.mypages.count-expr-field|The count expression field name of the parameter.|
|spring.mypages.count-method-name-field|The `count-method-name` field name of the parameter.|
|spring.mypages.enable-count-field|The `enable-count` field name of the parameter.|
|spring.mypages.skip-query-if-count-equals-zero|Whether to skip query if total value equals zero.|
|spring.mypages.use-generated-if-count-method-is-missing|Whether to use generated if the specified count method is missing.|
|spring.mypages.default-page|Replaces the `page` with `default-page` if the `page` is `null` or less than `1`.|
|spring.mypages.default-size|Replaces the `size` with `default-size` if the `size` is `null` or less than `1`.|
|spring.mypages.max-page|Replaces the `page` with `max-page` if the `page` is greater than `max-page`.|
|spring.mypages.max-size|Replaces the `size` with `max-size` if the `size` is greater than `max-size`.|

## For example

Define a class with paging parameters.

```java
public class Pageable {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer rows;
    private String countExpr;
    private String countMethodName;
    private Boolean enableCount;
    /* getter setter */
}
```

**Please notice that the sql-dialect is required.**

Properties

```properties
spring.mypages.enabled=true
spring.mypages.sql-dialect=MYSQL
spring.mypages.count-expr=*
spring.mypages.page-field=page
spring.mypages.size-field=size
spring.mypages.offset-field=offset
spring.mypages.rows-field=rows
spring.mypages.count-expr-field=countExpr
spring.mypages.count-method-name-field=countMethodName
spring.mypages.enable-count-field=enableCount
spring.mypages.skip-query-if-count-equals-zero=true
spring.mypages.use-generated-if-count-method-is-missing=true
spring.mypages.default-page=1
spring.mypages.default-size=10
spring.mypages.max-page=10000
spring.mypages.max-size=10000
```

Yaml

```yaml
spring:
  mypages:
    enabled: true
    sql-dialect: MYSQL
    count-expr: '*'
    page-field: page
    size-field: size
    offset-field: offset
    rows-field: rows
    count-expr-field: countExpr
    count-method-name-field: countMethodName
    enable-count-field: enableCount
    skip-query-if-count-equals-zero: true
    use-generated-if-count-method-is-missing: true
    default-page: 1
    default-size: 10
    max-page: 10000
    max-size: 10000
```