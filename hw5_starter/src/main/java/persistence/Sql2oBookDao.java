package persistence;

import exception.DaoException;
import model.Book;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oBookDao implements BookDao {
    private final Sql2o sql2o;

    public Sql2oBookDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Book book) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Books (id, title, isbn, publisher, year, authorId)" +
                    "VALUES (NULL, :title, :isbn, :publisher, :year, :authorId)";
            int id = (int) con.createQuery(query, true)
                    .bind(book)
                    .executeUpdate().getKey();
            book.setId(id);
            return id;
        }
    }

    @Override
    public List<Book> listAll() throws DaoException {
        String sql = "SELECT * FROM Books";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Book.class);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean update(Book book) throws DaoException {
        String sql = "Update Books SET title = :title, authorId = :authorId, " +
                "publisher = :publisher, year = :year WHERE isbn = :isbn";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(book)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Book author) throws DaoException {
        String sql = "DELETE FROM Books WHERE isbn =:isbn";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(author)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
