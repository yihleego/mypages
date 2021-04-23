# MyPages

MyPages is a java based, open source pagination plugin for [MyBatis](https://github.com/mybatis/mybatis-3) that simplifies database paging queries.
Many databases, one plugin.

## Installation

Please make sure the Java version is 1.8 and above.

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
    <version>1.1.2</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:1.1.2'
```

## Supported

|Database|Supported|Database|Supported|Database|Supported|
|:-:|:-:|:-:|:-:|:-:|:-:|
|MySQL|✔|PostgreSQL|✔|Oracle|✔|
|MariaDB|✔|SQLite|✔|DB2|✔|
|H2|✔|Phoenix|✔|Apache Derby|✔|
|HyperSQL|✔|Informix|✔|TiDB|✔|
|SQL Server|✔|Apache Hive|✔|

## Documentation

[Chinese Document 中文版](README.ZH_CN.md)

> * [User Guide (English)](docs/USERGUIDE.md)
> * [用户指南 (中文版)](docs/USERGUIDE.ZH_CN.md)
> * [mypages-spring-boot-starter (English)](docs/STARTER_USERGUIDE.md)
> * [mypages-spring-boot-starter (中文版)](docs/STARTER_USERGUIDE.ZH_CN.md)

## Contact

> * Bugs & Issues: [Click Here](https://github.com/yihleego/mypages/issues)

## License
MyPages is under the Apache 2.0 license. See the [LICENSE](LICENSE.txt) file for details.