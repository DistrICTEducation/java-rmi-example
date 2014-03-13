package remote;

import exception.DuplicateException;
import exception.BookNotFoundException;
import java.rmi.RemoteException;
import java.util.List;
import serializable.Book;

/**
 * The Library class manages a collection of items. Items can be added or
 * removed.
 * @author Joris Schelfaut, Gertjan Vanthienen
 */
public interface IRemoteLibrary {
    
    /**
     * Adds the given book to the library.
     * @param book the item to be added.
     * @throws RemoteException
     * @throws NullPointerException if the book resolves as NULL.
     * @throws DuplicateException if the book is already present in the library.
     */
    public void addBook (Book book) throws RemoteException,
            NullPointerException, DuplicateException;
    
    /**
     * Removes the given book from the library.
     * @param book the book to be removed from the library.
     * @throws RemoteException
     */
    public void removeBook (Book book) throws RemoteException;
    
    /**
     * Removes the book with given ISBN from the library.
     * @param isbn the ISBN of the book to be removed from the library.
     * @throws RemoteException
     */
    public void removeBook (String isbn) throws RemoteException;
    
    /**
     * Find a book with given ISBN in the library.
     * @param isbn the ISBN of the book.
     * @return the book with corresponding ISBN.
     * @throws RemoteException
     * @throws BookNotFoundException
     */
    public Book lookupBook (String isbn) throws RemoteException, BookNotFoundException;
    
    /**
     * @return the list of items.
     * @throws RemoteException
     */
    public List<Book> getBooks() throws RemoteException;
}
