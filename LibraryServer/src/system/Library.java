package system;

import interfaces.serializable.User;
import exceptions.BookNotFoundException;
import exceptions.DuplicateException;
import exceptions.UserNotFoundException;
import interfaces.serializable.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Library class is an implementation of the IRemoteLibrary interface, which
 * manages a collection of items. Items can be added or removed.
 * 
 * @author Joris Schelfaut
 */
public class Library {

    private final List<Book> books;
    private final List<User> users;
    
    /**
     * Instantiates a new Library object.
     */
    public Library() {
        super();
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    
    public synchronized void addBook (Book book)
            throws NullPointerException, DuplicateException {
        if (book == null) throw new NullPointerException("The book resolved as NULL.");
        if (this.books.contains(book)) throw new DuplicateException(book);
        this.books.add(book);
    }
    
    public void removeBook (Book book) {
        this.books.remove(book);
    }
    
    public void removeBook (String isbn) {
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn)) this.books.remove(b);
        }
    }
    
    public Book lookupBook (String isbn) throws BookNotFoundException {
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn)) return b;
        }
        throw new BookNotFoundException(isbn);
    }
    
    public List<Book> getBooks() {
        return Collections.unmodifiableList(this.books);
    }

    /**
     * @param user the user to add to the library.
     * @throws NullPointerException
     * @throws DuplicateException
     */
    public synchronized void addUser(User user)
            throws NullPointerException, DuplicateException {
        if (user == null) throw new NullPointerException("The user resolved as NULL.");
        if (this.users.contains(user))
            throw new DuplicateException("The user with name '" + user.getName()
                    + "' is already in the library.");
        this.users.add(user);
    }

    /**
     * @return the users of the library.
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(this.users);
    }
    
    /**
     * @param name the name of the user to look for in the library.
     * @return the user with given name.
     * @throws UserNotFoundException 
     */
    public User lookupUser (String name) throws UserNotFoundException {
        for (User u : this.users) {
            if (u.getName().equals(name)) return u;
        }
        throw new UserNotFoundException(name);
    }
    
    /**
     * @param user the user to be removed.
     */
    public void removeUser (User user) {
        this.users.remove(user);
    }
    
    /**
     * @param name the name of the user to be removed.
     */
    public void removeUser (String name) {
        for (User u : this.users) {
            if (u.getName().equals(name)) this.users.remove(u);
        }
    }
}
