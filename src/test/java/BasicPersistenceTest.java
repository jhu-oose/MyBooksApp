import model.Author;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.AuthorPersister;
import persistence.GsonAuthorPersister;
import persistence.Persister;
import persistence.PropertyAuthorPersister;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BasicPersistenceTest {
    private Persister<Author> p;
    private static Author a1;
    private static Author a2;

    @BeforeClass
    public static void beforeTest() {
        a1 = new Author("J.D Salinger", 8, "American");
    }

    @Test
    public void testAuthorPersistence() throws IOException {
        p = new AuthorPersister();
        p.serialize(a1);
        a2 = p.deserialize();
        assertEquals(a2.getName(), a1.getName());
        assertEquals(a2.getNumOfBooks(), a1.getNumOfBooks());
        assertEquals(a2.getNationality(), a1.getNationality());
    }

    @Test
    public void testPropertyAuthorPersistence() throws IOException {
        p = new PropertyAuthorPersister();
        p.serialize(a1);
        a2 = p.deserialize();
        assertEquals(a2.getName(), a1.getName());
        assertEquals(a2.getNumOfBooks(), a1.getNumOfBooks());
        assertEquals(a2.getNationality(), a1.getNationality());
    }

    @Test
    public void testGsonAuthorPersistence() throws IOException {
        p = new GsonAuthorPersister();
        p.serialize(a1);
        a2 = p.deserialize();
        assertEquals(a2.getName(), a1.getName());
        assertEquals(a2.getNumOfBooks(), a1.getNumOfBooks());
        assertEquals(a2.getNationality(), a1.getNationality());
    }



}
