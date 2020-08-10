# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 and above.

# 3. Dependency

## Maven

```xml
<properties>
    <mypages-spring-boot-starter.version>0.6.0</mypages-spring-boot-starter.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages-spring-boot-starter</artifactId>
    <version>${mypages-spring-boot-starter.version}</version>
</dependency>
```

## Gradle

```xml
implementation 'io.leego:mypages-spring-boot-starter:0.6.0'
```

# 4. Configuration

Define a class with paging parameters, and configure parameters field names.

```java
public class Pageable {
    private Integer page;
    private Integer size;
    private Integer offset;
    private Integer rows;
    private String countExpr;
    private String countMethodName;
    private boolean enableCount;
    /* getter setter */
}
```

Notice that the sql-dialect is required. It can be any SqlDialect and should be configured.

## Properties

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

## Yaml

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