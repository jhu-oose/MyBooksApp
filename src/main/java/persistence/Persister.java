package persistence;

import java.io.IOException;

/**
 * A simple interface for writing object persister.
 * @param <T> base data type.
 */
public interface Persister<T> {
    /**
     * Write out a single object instance to a file.
     * @param item To be stored in a file
     * @throws IOException when something goes wrong with serialization.
     */
    void serialize(T item) throws IOException;

    /**
     * Reading a single object instance from a file.
     * @return the item read from file.
     * @throws IOException when something goes wrong with serialization.
     */
    T deserialize() throws IOException;
}