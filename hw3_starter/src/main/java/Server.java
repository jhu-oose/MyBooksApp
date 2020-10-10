import com.google.gson.Gson;
import model.Author;
import model.Book;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import static spark.Spark.*;

public class Server {

    private static Sql2o getSql2o() {
        // set on foreign keys
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");

        // create data source
        SQLiteDataSource ds = new SQLiteDataSource(config);
        ds.setUrl("jdbc:sqlite:MyBooksApp.db");

        return new Sql2o(ds);
    }

    public static void main(String[] args)  {
        // set port number
        final int PORT_NUM = 7000;
        port(PORT_NUM);

        // root route; show a simple message!
        get("/", (req, res) -> "Welcome to MyBooksApp");

        // authors route; return list of authors as JSON
        get("/authors", (req, res) -> {
            Sql2oAuthorDao sql2oAuthor = new Sql2oAuthorDao(getSql2o());
            String results = new Gson().toJson(sql2oAuthor.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //addauthor route; add a new author
        post("/addauthor", (req, res) -> {
            String name = req.queryParams("name");
            int numOfBooks = Integer.parseInt(req.queryParams("numOfBooks"));
            String nationality = req.queryParams("nationality");
            Author a = new Author(name, numOfBooks, nationality);
            new Sql2oAuthorDao(getSql2o()).add(a);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        // Solution starts
        get("/books", (req, res) -> {
            Sql2oBookDao sql2oBook = new Sql2oBookDao(getSql2o());
            String results = new Gson().toJson(sql2oBook.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        post("/addbook", (req, res) -> {
            String title = req.queryParams("title");
            int year = Integer.parseInt(req.queryParams("year"));
            String publisher = req.queryParams("publisher");
            String isbn = req.queryParams("isbn");
            int authorId = Integer.parseInt(req.queryParams("authorId"));

            Book b = new Book(title, isbn, publisher, year, authorId);
            new Sql2oBookDao(getSql2o()).add(b);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(b.toString());
        });


        post("/delauthor", (req, res) -> {
            String name = req.queryParams("name");
            Author a = new Author(name, 0, "");
            new Sql2oAuthorDao(getSql2o()).delete(a);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        post("/delbook", (req, res) -> {
            String isbn = req.queryParams("isbn");
            Book b = new Book("", isbn, "", 1990, 1);
            new Sql2oBookDao(getSql2o()).delete(b);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(b.toString());
        });


    }
}
