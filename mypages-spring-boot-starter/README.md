# 1. Introduction

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.

# 2. Getting Started

Please make sure the Java version is 1.8 or above.

# 3. Maven Dependency

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages-spring-boot-starter</artifactId>
    <version>${mypages-spring-boot-starter.version}</version>
</dependency>
```

# 4. Configuration

|Property|Description|Default|
|:-|:-|:-|
|mypages.enabled|Whether to enable the plugin|`true`|
|mypages.database|The database type such as `MySQL`, `PostgreSQL`, `Oracle`, etc||
|mypages.count-expr|The column name or expression|`*`|
|mypages.page-field|The `page` field name of the parameter||
|mypages.size-field|The `size` field name of the parameter||
|mypages.offset-field|The `offset` field name of the parameter||
|mypages.rows-field|The `rows` field name of the parameter||
|mypages.count-expr-field|The count expression field name of the parameter||
|mypages.count-method-name-field|The `count-method-name` field name of the parameter||
|mypages.enable-count-field|The `enable-count` field name of the parameter||
|mypages.skip-query-if-count-equals-zero|Whether to skip query if total value equals zero|`true`|
|mypages.use-generated-if-count-method-is-missing|Whether to use generated if the specified count method is missing|`true`|
|mypages.use-graceful-count-sql|Whether to use graceful count-sql|`true`|
|mypages.keep-sorting|Whether to keep sorting|`false`|
|mypages.default-page|Replaces the `page` with `default-page` if the `page` is `null` or less than `1`|`-1`|
|mypages.default-size|Replaces the `size` with `default-size` if the `size` is `null` or less than `1`|`-1`|
|mypages.max-page|Replaces the `page` with `max-page` if the `page` is greater than `max-page`|`-1`|
|mypages.max-size|Replaces the `size` with `max-size` if the `size` is greater than `max-size`|`-1`|

### Example

Properties

```properties
mypages.enabled=true
mypages.database=MYSQL
mypages.count-expr=*
mypages.page-field=page
mypages.size-field=size
mypages.offset-field=offset
mypages.rows-field=rows
mypages.count-expr-field=countExpr
mypages.count-method-name-field=countMethodName
mypages.enable-count-field=enableCount
mypages.skip-query-if-count-equals-zero=true
mypages.use-generated-if-count-method-is-missing=true
mypages.use-graceful-count-sql=true
mypages.keep-sorting=false
mypages.default-page=1
mypages.default-size=10
mypages.max-page=10000
mypages.max-size=10000
```

Yaml

```yaml
mypages:
  enabled: true
  database: MYSQL
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
  use-graceful-count-sql: true
  keep-sorting: false
  default-page: 1
  default-size: 10
  max-page: 10000
  max-size: 10000
```