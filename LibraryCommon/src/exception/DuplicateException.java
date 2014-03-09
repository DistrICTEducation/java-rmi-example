package exception;

import serializable.Book;

/**
 * @author Joris Schelfaut
 */
public class DuplicateException extends RuntimeException {
    
    /**
     * Instantiates a new DuplicateException.
     * 
     * @param book 
     */
    public DuplicateException(Book book) {
        super("The item with title '" + book.getTitle()
            + "' is already in the library.");
    }    
}
