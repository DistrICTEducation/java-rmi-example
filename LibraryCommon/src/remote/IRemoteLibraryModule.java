package remote;

import exceptions.AuthorizationException;
import exceptions.DuplicateException;
import exceptions.BookNotFoundException;
import java.rmi.RemoteException;
import java.util.List;
import args.Book;
import args.Session;
import java.rmi.Remote;

/**
 * IRemoteLibraryModule manages a collection of books. Books can be added or
 * removed.
 * 
 * @author Joris Schelfaut, Gertjan Vanthienen
 */
public interface IRemoteLibraryModule extends Remote {
    
    /**
     * Adds the given book to the library.
     * @param book the item to be added.
     * @param session the session for the user invoking the operation.
     * @throws RemoteException
     * @throws NullPointerException if the book resolves as NULL.
     * @throws DuplicateException if the book is already present in the library.
     * @throws AuthorizationException the user was not authorized for this operation.
     */
    public void addBook (Book book, Session session) throws RemoteException,
            NullPointerException, DuplicateException, AuthorizationException;
    
    /**
     * Removes the given book from the library.
     * @param book the book to be removed from the library.
     * @param session the session for the user invoking the operation.
     * @throws RemoteException
     * @throws AuthorizationException
     */
    public void removeBook (Book book, Session session)
            throws RemoteException, AuthorizationException;
    
    /**
     * Removes the book with given ISBN from the library.
     * @param isbn the ISBN of the book to be removed from the library.
     * @param session the session for the user invoking the operation.
     * @throws RemoteException
     * @throws AuthorizationException
     */
    public void removeBook (String isbn, Session session)
            throws RemoteException, AuthorizationException;
    
    /**
     * Find a book with given ISBN in the library.
     * @param isbn the ISBN of the book.
     * @param owner the owner of the book.
     * @return the book with corresponding ISBN.
     * @throws RemoteException
     * @throws BookNotFoundException
     */
    public Book lookupBook (String isbn, String owner)
            throws RemoteException, BookNotFoundException;
    
    /**
     * @return the list of items.
     * @throws RemoteException
     */
    public List<Book> getBooks() throws RemoteException;
    
    /**
     * @param isbn the ISBN of the book.
     * @return the owners for a book with given ISBN.
     * @throws RemoteException 
     */
    public List<String> getOwnersForBook(String isbn) throws RemoteException;
    
    /**
     * @param owner the owner of the books.
     * @return the books of a given owner.
     * @throws RemoteException
     */
    public List<Book> getBooksForOwner(String owner) throws RemoteException;
}
