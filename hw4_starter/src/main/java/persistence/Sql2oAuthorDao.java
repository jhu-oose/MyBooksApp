package persistence;

import exception.DaoException;
import model.Author;
import java.util.List;
import org.sql2o.*;

public class Sql2oAuthorDao implements AuthorDao {

    private final Sql2o sql2o;

    public Sql2oAuthorDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public int add(Author author) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Authors (name, numOfBooks, nationality)" +
                    "VALUES (:name, :numOfBooks, :nationality)";
            int id = (int) con.createQuery(query, true)
                    .bind(author)
                    .executeUpdate().getKey();
            author.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public List<Author> listAll() throws DaoException {
        String sql = "SELECT * FROM Authors";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Author.class);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean update(Author author) throws DaoException {
        String sql = "Update Authors SET name = :name, numOfBooks = :numOfBooks, " +
                     "nationality = :nationality WHERE name = :name";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(author)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
    @Override
    public boolean delete(Author author) throws DaoException {
        String sql = "DELETE FROM Authors WHERE name =:name";
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
