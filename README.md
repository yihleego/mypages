# MyPages

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries. Many databases, one plugin.

## Installation

Please make sure the Java version is 1.8 or above.

```bash
$ git clone https://github.com/yihleego/mypages.git
$ cd mypages
$ mvn clean install
```

## Dependency

### Maven

```xml
<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>2.1.0</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:2.1.0'
```

## Supported

|Database|
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

**ANTLR** (ANother Tool for Language Recognition) is a powerful parser generator for reading, processing, executing, or translating structured text or binary files. It's widely used to build languages, tools, and frameworks. From a grammar, ANTLR generates a parser that can build and walk parse trees.

### Original SQL

```sql
select * from t where k = v
```

![Query Statement Tree](docs/sql/query_tree.png)

### Generated SQL

```sql
select count(*) from t where k = v
```

![Count Statement Tree](docs/sql/count_tree.png)

## Documentation

[Chinese Document 中文版](README.ZH_CN.md)

> * [User Guide (English)](mypages/README.md)
> * [用户指南 (中文版)](mypages/README.ZH_CN.md)
> * [mypages-spring-boot-starter (English)](mypages-spring-boot-starter/README.md)
> * [mypages-spring-boot-starter (中文版)](mypages-spring-boot-starter/README.ZH_CN.md)

## Contact

> * Bugs & Issues: [Click Here](https://github.com/yihleego/mypages/issues)

## License

MyPages is under the Apache 2.0 license. See the [LICENSE](LICENSE.txt) file for details.