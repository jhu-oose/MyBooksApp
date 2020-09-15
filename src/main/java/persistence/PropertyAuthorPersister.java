package persistence;

import model.Author;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyAuthorPersister implements Persister<Author> {

    private final static String STORE =  "Author_p.txt";

    @Override
    public void serialize(Author author) throws IOException {
        Properties p = new Properties();

        p.setProperty("name", author.getName());
        p.setProperty("numOfBooks", String.valueOf(author.getNumOfBooks()));
        p.setProperty("nationality", author.getNationality());

        FileWriter fw = new FileWriter(STORE);
        p.store(fw, "comment"); // replace "comment" with any relevant comment!
        fw.close();
    }

    @Override
    public Author deserialize() throws IOException {
        Properties p = new Properties();
        FileReader fr = new FileReader(STORE);
        p.load(fr);
        fr.close();

        String name = p.getProperty("name");
        String numOfBooks = p.getProperty("numOfBooks");
        String nationality = p.getProperty("nationality");

        return new Author(name, Integer.parseInt(numOfBooks), nationality);
    }

}