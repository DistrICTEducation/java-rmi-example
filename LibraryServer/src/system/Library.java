package system;

import exceptions.BookNotFoundException;
import exceptions.DuplicateException;
import exceptions.UserNotFoundException;
import args.Book;
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
    
    public Book lookupBook (String isbn, String owner) throws BookNotFoundException {
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn) && b.getOwner().equalsIgnoreCase(owner))
                return b;
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
    
    /**
     * @param isbn the ISBN of the book.
     * @return the list of names of owners of the book with given ISBN.
     */
    public List<String> getOwnersForBook(String isbn) {
        List<String> owners = new ArrayList<>();
        for (Book b : this.books) {
            if (b.getISBN().equals(isbn)) owners.add(b.getOwner());
        }
        return owners;
    }
    
    /**
     * @param owner the owners to get the books from.
     * @return the list of books owned by a user given name.
     */
    public List<Book> getBooksForOwner(String owner) {
        List<Book> booklist = new ArrayList<>();
        for (Book b : this.books) {
            if (b.getOwner().equals(owner)) booklist.add(b);
        }
        return booklist;
    }
}
