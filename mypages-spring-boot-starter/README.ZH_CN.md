# 1. 简介

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

# 2. 准备工作

请确保您的Java版本在1.8及以上。

# 3. Maven 依赖

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages-spring-boot-starter</artifactId>
    <version>${mypages-spring-boot-starter.version}</version>
</dependency>
```

# 4. 配置

|Property|Description|Default|
|:-|:-|:-|
|mypages.enabled|是否启用分页插件|`true`|
|mypages.database|指定数据库类型，如：`MySQL`、`PostgreSQL`、`Oracle`等||
|mypages.count-expr|设置count表达式|`*`|
|mypages.page-field|从参数字段中获取`page`值||
|mypages.size-field|从参数字段中获取`size`值||
|mypages.offset-field|从参数字段中获取`offset`值||
|mypages.rows-field|从参数字段中获取`rows`值||
|mypages.count-expr-field|从参数字段中获取count表达式||
|mypages.count-method-name-field|从参数字段中获取自定义count方法名称||
|mypages.enable-count-field|从参数字段中判断是否启用count||
|mypages.skip-query-if-count-equals-zero|是否跳过查询如果结果数量为`0`|`true`|
|mypages.use-generated-if-count-method-is-missing|是否使用自动生成的count方法如果指定count方法不存在|`true`|
|mypages.use-graceful-count-sql|是否使用优雅的Count-SQL|`true`|
|mypages.keep-sorting|是否保留排序|`false`|
|mypages.default-page|如果`page`为空或小于`1`，则用`default-page`替换`page`，`-1`表示不启用|`-1`|
|mypages.default-size|如果`size`为空或小于`1`，则用`default-size`替换`size`，`-1`表示不启用|`-1`|
|mypages.max-page|如果`page`大于`max-page`，则用`max-page`替换`page`，`-1`表示不启用|`-1`|
|mypages.max-size|如果`size`大于`max-size`，则用`max-size`替换`size`，`-1`表示不启用|`-1`|

### 示例

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