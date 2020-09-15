package model;

import java.util.List;
import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private int numOfBooks;
    private String nationality;

    public Author(String name, int numOfBooks, String nationality) {
        this.name = name;
        this.numOfBooks = numOfBooks;
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                numOfBooks == author.numOfBooks &&
                Objects.equals(name, author.name) &&
                Objects.equals(nationality, author.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numOfBooks, nationality);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numOfBooks=" + numOfBooks +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfBooks(int numOfBooks) {
        this.numOfBooks = numOfBooks;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumOfBooks() {
        return numOfBooks;
    }

    public String getNationality() {
        return nationality;
    }
}
