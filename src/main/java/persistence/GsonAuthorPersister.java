package persistence;

import model.Author;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonAuthorPersister implements Persister<Author> {

    private final String STORE = "Author_gson";
    private final Gson gson;

    public GsonAuthorPersister() {
        gson = new Gson();
    }



    @Override
    public void serialize(Author author) throws IOException {
        FileWriter fw = new FileWriter(STORE);
        gson.toJson(author, fw);
        fw.close();
    }

    @Override
    public Author deserialize() throws IOException {
        FileReader fr = new FileReader(STORE);
        Author author = gson.fromJson(fr, Author.class);
        fr.close();
        return author;
    }
}