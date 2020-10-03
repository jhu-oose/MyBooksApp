import model.Author;
import model.Book;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class DBDaoCRUDTest {
    final static String URI = "jdbc:sqlite:./MyBooksApp.db";
    static Sql2o sql2o;
    static Sql2oAuthorDao authorDao;
    static Sql2oBookDao bookDao;


    @BeforeClass
    public static void beforeClassTests() {
        sql2o = new Sql2o(URI,"","");
        authorDao = new Sql2oAuthorDao(sql2o);
        bookDao = new Sql2oBookDao(sql2o);
        String sqlDropAuthors = "DROP TABLE IF EXISTS Authors";
        String sqlDropBooks = "DROP TABLE IF EXISTS Books";
        String sqlCreateAuthors = "CREATE TABLE IF NOT EXISTS Authors (id INTEGER PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE," +
                "numOfBooks INTEGER, nationality VARCHAR(30));";
        String sqlCreateBooks = "CREATE TABLE IF NOT EXISTS Books (id INTEGER PRIMARY KEY, title VARCHAR(200) NOT NULL,"+
                "isbn VARCHAR(14) NOT NULL UNIQUE, publisher VARCHAR(14), year INTEGER,"+
                "authorId INTEGER NOT NULL, FOREIGN KEY(authorId) REFERENCES Authors(id)"+
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        try (Connection con = sql2o.open()) {
            con.createQuery(sqlDropBooks).executeUpdate();
            con.createQuery(sqlDropAuthors).executeUpdate();
            con.createQuery(sqlCreateAuthors).executeUpdate();
            con.createQuery(sqlCreateBooks).executeUpdate();
        }
    }

    @Test
    public void testAuthorAdd() {
        Author author = new Author("George Orwell", 13, "British");
        assertTrue(authorDao.add(author) > 0);
    }

    @Test
    public void testAuthorListAll() {
        assertNotEquals(null, authorDao.listAll());
    }

    @Test
    public void testAuthorUpdate() {
        Author author = new Author("George Orwell", 16, "British");
        assertTrue(authorDao.update(author));
    }

    @Test
    public void testAuthorDelete() {
        Author author = new Author("Franz Kafka", 16, "British");
        assertTrue(authorDao.delete(author));
    }


    // Book Tests
    @Test
    public void testBookAdd() {
        Author author = new Author("Franz Kafka", 16, "Czechoslovakian");
        assertTrue(authorDao.add(author) > 0);
        Book book =  new Book("The Trial","978-0805210408", "Schocken", 1995, 1);
        assertTrue(bookDao.add(book) > 0);
    }

    @Test
    public void testBookListAll() {
        assertNotEquals(null, bookDao.listAll());
    }

    @Test
    public void testBookUpdate() {
        Book book =  new Book("The Trial","978-0805210408", "Schocken", 1992, 1);
        assertTrue(bookDao.update(book));
    }

    @Test
    public void testBookDelete() {
        Book book =  new Book("The Trial","978-0805210408", "Schocken", 1992, 1);
        assertTrue(bookDao.delete(book));
    }

}
