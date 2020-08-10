# MyPages

[English Document 英文版](README.md)

MyPages是Java实现基于[MyBatis](https://github.com/mybatis/mybatis-3)的开源分页插件，最大程度简化数据库分页查询操作，支持市面上大部分数据库，如：MySQL、PostgreSQL、Oracle、SQLServer等。

## 安装

```bash
$ git clone https://github.com/yihleego/mypages.git
$ cd mypages
$ mvn clean install
```

## 依赖

### Maven

```xml
<properties>
    <mypages.version>0.6.0</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:0.6.0'
```

## 数据库

|数据库|支持|数据库|支持|数据库|支持|
|:-:|:-:|:-:|:-:|:-:|:-:|
|MySQL|✔|PostgreSQL|✔|Oracle|✔|
|MariaDB|✔|SQLite|✔|DB2|✔|
|H2|✔|Phoenix|✔|Apache Derby|✔|
|HyperSQL|✔|Informix|✔|TiDB|✔|
|SQL Server|✔|Apache Hive|✔|

## 文档

> * [User Guide (English)](docs/USERGUIDE.md)
> * [用户指南 (中文版)](docs/USERGUIDE.ZH_CN.md)
> * [MyPages-Spring-Boot-Starter (English)](docs/STARTER_USERGUIDE.md)
> * [MyPages-Spring-Boot-Starter (中文版)](docs/STARTER_USERGUIDE.ZH_CN.md)

## 交流

> * 问题与意见: [点击这里](https://github.com/yihleego/mypages/issues)

## License
MyPages is under the Apache 2.0 license. See the [LICENSE](LICENSE.txt) file for details.