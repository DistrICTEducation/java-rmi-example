package system;

import exceptions.AuthorizationException;
import exceptions.BookNotFoundException;
import exceptions.DuplicateException;
import java.rmi.RemoteException;
import java.util.List;
import remote.IRemoteLibraryModule;
import remote.IRemoteSessionModule;
import args.Book;
import args.Session;

/**
 * Implementation for the IRemoteLibraryModule. The LibraryModule is stateless.
 * The module allows to perform certain operations on the library.
 * 
 * @author Joris Schelfaut
 */
public class LibraryModule implements IRemoteLibraryModule {
    
    private final Library library;
    private final IRemoteSessionModule sessionModule;

    /**
     * Instantiates a new LibraryModule object.
     * @param library the library that is managed by the LibraryModule instance.
     * @param irsm the session module used for authorization.
     */
    public LibraryModule(Library library, IRemoteSessionModule irsm) {
        this.library = library;
        this.sessionModule = irsm;
    }

    @Override
    public void addBook(Book book, Session session) throws RemoteException,
            NullPointerException, DuplicateException, AuthorizationException {
        if (! this.sessionModule.isAuthenticated(session))
            throw new AuthorizationException(session);
        if (! book.getOwner().equalsIgnoreCase(session.getUsername()))
            throw new AuthorizationException(session);
        this.library.addBook(book);
    }
    
    @Override
    public void removeBook(Book book, Session session) throws RemoteException,
            AuthorizationException {
        if (! this.sessionModule.isAuthenticated(session))
            throw new AuthorizationException(session);
        if (! book.getOwner().equalsIgnoreCase(session.getUsername()))
            throw new AuthorizationException(session);
        this.library.removeBook(book);
    }

    @Override
    public void removeBook(String isbn, Session session) throws RemoteException,
            AuthorizationException {
        if (! this.sessionModule.isAuthenticated(session))
            throw new AuthorizationException(session);
        try {
            this.library.lookupBook(isbn, session.getUsername());
        } catch (BookNotFoundException e) {
            throw new AuthorizationException(session);
        }
        this.library.removeBook(isbn);
    }

    @Override
    public Book lookupBook(String isbn, String owner)
            throws RemoteException, BookNotFoundException {
        return this.library.lookupBook(isbn, owner);
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return this.library.getBooks();
    }

    @Override
    public List<String> getOwnersForBook(String isbn) throws RemoteException {
        return this.library.getOwnersForBook(isbn);
    }

    @Override
    public List<Book> getBooksForOwner(String owner) throws RemoteException {
        return this.library.getBooksForOwner(owner);
    }
}