# MyPages

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

## 安装

请确保您的Java版本在1.8及以上。

```bash
$ git clone https://github.com/yihleego/mypages.git
$ cd mypages
$ mvn clean install
```

## 依赖

### Maven

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>2.2.0</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:2.2.0'
```

## 支持数据库

|数据库|
|:---|
|![PostgreSQL](docs/databases/postgresql.svg) PostgreSQL|
|![MySQL](docs/databases/mysql.svg) MySQL|
|![Oracle](docs/databases/oracle.svg) Oracle|
|![Microsoft SQL Server](docs/databases/sql_server.svg) Microsoft SQL Server|
|![SQLite](docs/databases/sqlite.svg) SQLite|
|![DB2](docs/databases/db2.svg) DB2|
|![H2](docs/databases/h2.svg) H2|
|![MariaDB](docs/databases/mariadb.svg) MariaDB|
|![HSQLDB](docs/databases/hsqldb.svg) HSQLDB|
|![Apache Phoenix](docs/databases/apache_phoenix.svg) Apache Phoenix|
|![Apache Derby](docs/databases/apache_derby.svg) Apache Derby|
|![Apache Hive](docs/databases/hive.svg) Apache Hive|
|![Informix](docs/databases/informix.svg) Informix|

## ANTLR

**ANTLR** (ANother Tool for Language Recognition) 是一个强大的解析器生成器，用于读取、处理、执行或翻译结构化文本或二进制文件。它被广泛用于构建语言、工具和框架。根据语法，ANTLR 生成可以构建和遍历解析树的解析器。

### 原始的SQL

```sql
select * from t where k = v
```

![Query Statement Tree](docs/sql/query_tree.png)

### 生成的SQL

```sql
select count(*) from t where k = v
```

![Count Statement Tree](docs/sql/count_tree.png)

## 文档

[English Document 英文版](README.md)

> * [用户指南 (中文版)](mypages/README.ZH_CN.md)
> * [User Guide (English)](mypages/README.md)
> * [mypages-spring-boot-starter (中文版)](mypages-spring-boot-starter/README.ZH_CN.md)
> * [mypages-spring-boot-starter (English)](mypages-spring-boot-starter/README.md)

## 交流

> * 问题与意见: [点击这里](https://github.com/yihleego/mypages/issues)

## License

MyPages is under the Apache 2.0 license. See the [LICENSE](LICENSE.txt) file for details.