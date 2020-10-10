import com.google.gson.Gson;
import model.Author;
import model.Book;
import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.sql.*;
import static org.junit.Assert.*;

public class RESTAPITest {

    static OkHttpClient client;
    @BeforeClass
    public static void beforeClassTests() throws SQLException {
        client = new OkHttpClient();
    }

    @Test
    public void testListAuthors() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @Test
    public void testAddAuthor() throws IOException {
        RequestBody postBody = new FormBody.Builder()
                .add("name", "Sadegh Hedayat")
                .add("numOfBooks", "26")
                .add("nationality", "Iranian")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(201, response.code());
    }

    // Solution starts
    @Test
    public void testListBooks() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        Response response = client.newCall(request).execute();
        // String resBody = response.body().string();
        // Book[] books = gson.fromJson(resBody, Book[].class);
        // loop through authors and do extra assertions
        assertEquals(200, response.code());
    }

    @Test
    public void testAddBook() throws IOException {
        RequestBody postBody = new FormBody.Builder()
                .add("title", "Animal Farm")
                .add("isbn", "9780451526342")
                .add("publisher", "Signet")
                .add("year", "1996")
                .add("authorId", "1")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(201, response.code());
    }


    @Test
    public void testDelAuthor() throws IOException {

        // 1. delete Franz Kafka
        RequestBody postBody = new FormBody.Builder()
                .add("name", "Franz Kafka")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:7000/delauthor")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        // 2. get all authors again; Kafka should have been deleted!
        request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        response = client.newCall(request).execute();
        String resBody = response.body().string();
        Author[] authorsAfterDel = new Gson().fromJson(resBody, Author[].class);
        for (Author a: authorsAfterDel) {
            if ("Franz Kafka".equals(a.getName())) {
                fail ("Franz Kafka not deleted!");
            }
        }
        assertEquals(200, response.code());
    }

    @Test
    public void testDelBook() throws IOException {

        // 2. delete 978-0451524935
        RequestBody postBody = new FormBody.Builder()
                .add("isbn", "978-0451524935")
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:7000/delbook")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        // 1. get all books
        request = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        response = client.newCall(request).execute();
        String resBody = response.body().string();
        Book[] booksAfterDel = new Gson().fromJson(resBody, Book[].class);

        for (Book b: booksAfterDel) {
            if ("978-0451524935".equals(b.getIsbn())) {
                fail ("978-0451524935 not deleted!");
            }
        }
        assertEquals(200, response.code());
    }




}