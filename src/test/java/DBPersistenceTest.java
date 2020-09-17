import model.Author;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;


public class DBPersistenceTest {

    private static String URI;
    private static Connection conn;
    private static Statement st;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {
        URI = "jdbc:sqlite:./MyBooksApp.db";
        conn = DriverManager.getConnection(URI);
        st = conn.createStatement();

        String sql = "DROP TABLE IF EXISTS Authors";
        st.execute(sql);
    }

    @Before
    public void beforeEachTest() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS Authors (id INTEGER PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE," +
                " numOfBooks INTEGER, nationality VARCHAR(30));";
        st.execute(sql);
    }

    @Test
    public void testInsertAuthor1() throws SQLException {
        String sql = "INSERT INTO Authors(id, name, numOfBooks, nationality)"+
                "VALUES (NULL, 'George Orwell', 15, 'British');";
        assertFalse(st.execute(sql)); // false expected! check execute specs
        sql = "Select * from Authors WHERE name=\"George Orwell\"";
        ResultSet rs = st.executeQuery(sql);
        assertTrue(rs.next());
    }

    @Test
    public void testInsertAuthor2() throws SQLException {
        String sql = "INSERT INTO Authors(id, name, numOfBooks, nationality)"+
                "VALUES (NULL, 'Emily St. John Mandel', 5, 'Canadian');";
        assertFalse(st.execute(sql)); // false expected! check execute specs
        sql = "Select * from Authors WHERE name=\"Emily St. John Mandel\"";
        ResultSet rs = st.executeQuery(sql);
        assertTrue(rs.next());
    }

    @Test
    public void testInsertAuthor3() throws SQLException {
        String sql = "INSERT INTO Authors(id, name, numOfBooks, nationality)"+
                "VALUES (NULL, 'J.D Salinger', 8, 'American');";
        assertFalse(st.execute(sql));
        sql = "Select * from Authors WHERE name=\"J.D Salinger\"";
        ResultSet rs = st.executeQuery(sql);
        assertTrue(rs.next());
    }

    @Test
    public void testParameterizedInsertAuthor() throws SQLException {
        Author a = new Author ("Franz Kafka", 16, "Czechoslovakian");
        String sql = "INSERT INTO Authors (id, name, numOfBooks, nationality)"+
                                "VALUES (NULL, ?, ?, ?);";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, a.getName());
        pst.setString(2, String.valueOf(a.getNumOfBooks()));
        pst.setString(3, a.getNationality());
        assertFalse(pst.execute());
        sql = "Select * from Authors WHERE name=\"Franz Kafka\"";
        ResultSet rs = st.executeQuery(sql);
        assertTrue(rs.next());
    }



}
