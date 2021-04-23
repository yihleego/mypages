# 1. 简介

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

# 2. 准备工作

请确保您的Java版本在1.8及以上。

# 3. 依赖

## 3.1 Maven

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages-spring-boot-starter</artifactId>
    <version>1.1.2</version>
</dependency>
```

## 3.2 Gradle

```xml
implementation 'io.leego:mypages-spring-boot-starter:1.1.2'
```

# 4. 配置

|Property|Description|
|:-|:-|
|spring.mypages.enabled|是否启用mypages。|
|spring.mypages.sql-dialect|指定sql dialect，如：`MySQL`、`PostgreSQL`、`Oracle`等。|
|spring.mypages.count-expr|设置count字段名称，默认值为`*`。|
|spring.mypages.page-field|从参数字段中获取`page`值。|
|spring.mypages.size-field|从参数字段中获取`size`值。|
|spring.mypages.offset-field|从参数字段中获取`offset`值。|
|spring.mypages.rows-field|从参数字段中获取`rows`值。|
|spring.mypages.count-expr-field|从参数字段中获取count表达式。|
|spring.mypages.count-method-name-field|从参数字段中获取自定义count方法名称。|
|spring.mypages.enable-count-field|从参数字段中判断是否启用count。|
|spring.mypages.skip-query-if-count-equals-zero|是否跳过查询如果结果数量为`0`。|
|spring.mypages.use-generated-if-count-method-is-missing|是否使用自动生成的count方法如果指定count方法不存在。|
|spring.mypages.default-page|如果`page`为空或小于`1`，则用`default-page`替换`page`。|
|spring.mypages.default-size|如果`size`为空或小于`1`，则用`default-size`替换`size`。|
|spring.mypages.max-page|如果`page`大于`max-page`，则用`max-page`替换`page`。|
|spring.mypages.max-size|如果`size`大于`max-size`，则用`max-size`替换`size`。|

## 示例

使用分页参数定义一个类，并配置参数字段名。

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

**请注意sql-dialect必须被配置。**

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