package system;

import exception.BookNotFoundException;
import exception.DuplicateException;
import serializable.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import remote.IRemoteLibrary;

/**
 * The Library class is an implementation of the IRemoteLibrary interface, which
 * manages a collection of items. Items can be added or removed.
 * 
 * @author Joris Schelfaut
 */
public class Library implements IRemoteLibrary {

    private final List<Book> books;
    
    /**
     * Instantiates a new Library object.
     */
    public Library() {
        super();
        this.books = new ArrayList<>();
    }
    
    @Override
    public synchronized void addBook (Book book)
            throws NullPointerException, DuplicateException {
        if (book == null) throw new NullPointerException("The item resolved as NULL.");
        if (this.books.contains(book)) throw new DuplicateException(book);
        this.books.add(book);
    }
    
    @Override
    public void removeItem (Book book) {
        this.books.remove(book);
    }
    
    @Override
    public void removeItem (String isbn) {
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn)) this.books.remove(b);
        }
    }
    
    @Override
    public Book lookup (String isbn) throws BookNotFoundException {
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn)) return b;
        }
        throw new BookNotFoundException(isbn);
    }
    
    @Override
    public List<Book> getBooks() {
        return Collections.unmodifiableList(this.books);
    }
}
