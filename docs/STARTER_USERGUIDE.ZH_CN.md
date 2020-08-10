# 1. 简介

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

# 2. 准备工作

请确保您的Java版本在1.8及以上。

# 3. 依赖

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

# 4. 配置

使用分页参数定义一个类，并配置参数字段名。

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

请注意sql-dialect，它可以是任何SqlDialect且必须被配置。

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