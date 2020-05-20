# MyPages

The MyPages is a java based, open source pagination plugin for MyBatis that simplifies database paging queries.
Many databases, one plugin.

## Dependency

```bash
$ git clone https://github.com/yihleego/mypages.git
$ cd mypages
$ mvn clean install
```

### Maven

```xml
<properties>
    <mypages.version>0.3.0</mypages.version>
</properties>

<dependency>
    <groupId>io.leego</groupId>
    <artifactId>mypages</artifactId>
    <version>${mypages.version}</version>
</dependency>
```

### Gradle

```xml
implementation 'io.leego:mypages:0.3.0'
```

## Supported

|Database|Supported|Database|Supported|Database|Supported|
|:-:|:-:|:-:|:-:|:-:|:-:|
|MySQL|✔|PostgreSQL|✔|Oracle|✔|
|MariaDB|✔|SQLite|✔|DB2|✔|
|H2|✔|Phoenix|✔|Apache Derby|✔|
|HyperSQL|✔|Informix|✔|TiDB|✔|
|SQL Server|✔|Apache Hive|✔|
|Snowflake|❌|Azure|❌|Exasol|❌|
|ClickHouse|❌|Cassandra|❌|Vertica|❌|
|Greenplum|❌|Sybase|❌|Amazon Redshift|❌|

## Documentation

> * [User Guide](https://github.com/yihleego/mypages/blob/master/USERGUIDE.md)

## Contact

> * Bugs: [Issues](https://github.com/yihleego/mypages/issues)


## License
MyPages is under the Apache 2.0 license. See the [LICENSE](https://github.com/yihleego/mypages/blob/master/LICENSE.txt) file for details.

