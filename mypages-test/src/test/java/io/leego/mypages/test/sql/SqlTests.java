package io.leego.mypages.test.sql;

import io.leego.mypages.util.SqlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

/**
 * @author Yihleego
 */
@SpringBootTest
public class SqlTests {
    private static final Logger logger = LoggerFactory.getLogger(SqlTests.class);

    @Test
    public void testSimple() {
        String original = "select id, name";
        String expected = "select COUNT(*)";
        test(original, expected);
    }

    @Test
    public void testAsterisk() {
        String original = "select * from foo";
        String expected = "select COUNT(*) from foo";
        test(original, expected);
    }

    @Test
    public void testColumnReference() {
        String original = "select id, name from foo";
        String expected = "select COUNT(*) from foo";
        test(original, expected);
    }

    @Test
    public void testSemicolon() {
        String original = "select id, name from foo;";
        String expected = "select COUNT(*) from foo;";
        test(original, expected);
    }

    @Test
    public void testWhitespace() {
        String original = " select id, name from foo ";
        String expected = " select COUNT(*) from foo ";
        test(original, expected);
    }

    @Test
    public void testUpperCaseKeyword() {
        String original = "SELECT id, name FROM foo";
        String expected = "SELECT COUNT(*) FROM foo";
        test(original, expected);
    }

    @Test
    public void testUpperCaseAll() {
        String original = "SELECT ID, NAME FROM FOO";
        String expected = "SELECT COUNT(*) FROM FOO";
        test(original, expected);
    }

    @Test
    public void testWhere() {
        String original = "select id, name from foo where id = 1 and name = 'name'";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name'";
        test(original, expected);
    }

    @Test
    public void testOrderBy() {
        String original = "select id, name from foo where id = 1 and name = 'name' order by name";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' ";
        test(original, expected);
    }

    @Test
    public void testLimit1() {
        String original = "select id, name from foo where id = 1 and name = 'name' limit 1, 1";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' ";
        test(original, expected);
    }

    @Test
    public void testLimit2() {
        String original = "select id, name from foo where id = 1 and name = 'name' limit 1 offset 1";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' ";
        test(original, expected);
    }

    @Test
    public void testOrderByLimit1() {
        String original = "select id, name from foo where id = 1 and name = 'name' order by name limit 1, 1";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' ";
        test(original, expected);
    }

    @Test
    public void testOrderByLimit2() {
        String original = "select id, name from foo where id = 1 and name = 'name' order by name limit 1 offset 1";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' ";
        test(original, expected);
    }

    @Test
    public void testOrderByLimitKeepSorting() {
        String original = "select id, name from foo where id = 1 and name = 'name' order by name limit 1 offset 1";
        String expected = "select COUNT(*) from foo where id = 1 and name = 'name' order by name ";
        test(original, expected, true);
    }

    @Test
    public void testGroupBy() {
        String original = "select name from foo where id = 1 and name = 'name' group by name";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testHaving() {
        String original = "select name from foo where id = 1 and name = 'name' having name is not null";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testGroupByHaving() {
        String original = "select name from foo where id = 1 and name = 'name' group by name having count(*) > 0";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testWithAs() {
        String original = "with temp as (select id, name from foo where name like 'name%')\n" +
                "select id, name from temp where id = 1";
        String expected = "with temp as (select id, name from foo where name like 'name%')\n" +
                "select COUNT(*) from temp where id = 1";
        test(original, expected);
    }

    @Test
    public void testMultipleWithAs() {
        String original = "with temp1 as (SELECT * FROM foo),\n" +
                "temp2 as (SELECT * FROM bar)\n" +
                "select temp1.id, temp1.name, temp2.id, temp2.name from temp1, temp2";
        String expected = "with temp1 as (SELECT * FROM foo),\n" +
                "temp2 as (SELECT * FROM bar)\n" +
                "select COUNT(*) from temp1, temp2";
        test(original, expected);
    }

    @Test
    public void testInsert() {
        String original = "insert into foo values(?, ?)";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testUpdate() {
        String original = "update foo set name = 'name' where id = 1";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testDelete() {
        String original = "delete from foo where id = 1";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testExample() {
        String original = "with u as (SELECT user.* FROM user),\n" +
                "     ue as (SELECT user_external.* FROM user_external),\n" +
                "     up as (SELECT user_profile.* FROM user_profile)\n" +
                "select u.id       as userId,\n" +
                "       u.username,\n" +
                "       ue.open_id as openId,\n" +
                "       up.gender,\n" +
                "       up.avatar\n" +
                "from u\n" +
                "         join ue on ue.user_id = u.id\n" +
                "         join up on up.user_id = u.id\n" +
                "where u.username = 'username'\n" +
                "order by u.id\n" +
                "limit 0,10";
        String expected = "with u as (SELECT user.* FROM user),\n" +
                "     ue as (SELECT user_external.* FROM user_external),\n" +
                "     up as (SELECT user_profile.* FROM user_profile)\n" +
                "select COUNT(*)\n" +
                "from u\n" +
                "         join ue on ue.user_id = u.id\n" +
                "         join up on up.user_id = u.id\n" +
                "where u.username = 'username'\n";
        test(original, expected);
    }

    @Test
    public void testExampleGroupBy() {
        String original = "with u as (SELECT user.* FROM user),\n" +
                "     ue as (SELECT user_external.* FROM user_external),\n" +
                "     up as (SELECT user_profile.* FROM user_profile)\n" +
                "select u.id       as userId,\n" +
                "       u.username,\n" +
                "       ue.open_id as openId,\n" +
                "       up.gender,\n" +
                "       up.avatar\n" +
                "from u\n" +
                "         join ue on ue.user_id = u.id\n" +
                "         join up on up.user_id = u.id\n" +
                "where u.username = 'username'\n" +
                "group by u.id, u.username, ue.open_id, up.gender, up.avatar\n" +
                "order by u.id\n" +
                "limit 0,10";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testSubQuery() {
        String original = "select u.id as id, u.name\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n";
        String expected = "select COUNT(*)\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n";
        test(original, expected);
    }

    @Test
    public void testSubQueryOrderBy() {
        String original = "select u.id as id, u.name\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n" +
                "order by u.id";
        String expected = "select COUNT(*)\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n";
        test(original, expected);
    }

    @Test
    public void testSubQueryOrderByLimit() {
        String original = "select u.id as id, u.name\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n" +
                "order by u.id\n" +
                "limit 0, 10";
        String expected = "select COUNT(*)\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n";
        test(original, expected);
    }

    @Test
    public void testSubQueryGroupByOrderByLimit() {
        String original = "select u.id as id, u.name\n" +
                "from (select id, name from foo) u\n" +
                "where u.name = 'name'\n" +
                "group by u.id, u.name\n" +
                "order by u.id\n" +
                "limit 0, 10";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testFunctionCall() {
        String original = "select max(id), avg(id), sum(id) from foo";
        String expected = null;
        test(original, expected);
    }

    @Test
    public void testSubQueryExpression() {
        String original = "(select id, name from foo where id = 1 and name = 'name')";
        String expected = "(select COUNT(*) from foo where id = 1 and name = 'name')";
        test(original, expected);
    }

    private void test(String original, String expected) {
        test(original, expected, false);
    }

    private void test(String original, String expected, boolean keepSorting) {
        String generated = SqlUtils.toCountSql(original, keepSorting);
        String color = Objects.equals(expected, generated) ? "\033[32;40m" : "\033[31;40m";
        logger.info("\n\n" +
                "==================== original sql ====================\n\033[40m{}\033[0m\n" +
                "==================== expected sql ====================\n\033[40m{}\033[0m\n" +
                "==================== generated sql ===================\n{}{}\033[0m\n" +
                "======================================================\n", toNull(original), toNull(expected), color, toNull(generated));
        Assertions.assertEquals(expected, generated);
    }

    private String toNull(String s) {
        if (s == null) {
            return "<null>";
        }
        return s;
    }
}
