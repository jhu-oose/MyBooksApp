package persistence;

import model.Author;

import model.Author;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class AuthorPersister implements Persister<Author> {

    private final static String STORE =  "Author.txt";

    public void serialize(Author author) throws IOException {
        FileWriter fw = new FileWriter(STORE);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(author.getName());
        bw.newLine();
        bw.write(String.valueOf(author.getNumOfBooks()));
        bw.newLine();
        bw.write(author.getNationality());
        bw.newLine();

        bw.close();
    }

    public Author deserialize() throws IOException {
        FileReader fr = new FileReader(STORE);
        BufferedReader br = new BufferedReader(fr);

        String name = br.readLine();
        int numOfBooks = Integer.parseInt(br.readLine());
        String nationality = br.readLine();

        return new Author(name, numOfBooks, nationality);
    }
}