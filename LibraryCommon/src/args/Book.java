package args;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Book class is an item with ISBN number.
 * @author Joris Schelfaut
 */
public class Book implements Serializable {

    private String title;
    private String author;
    private int year;
    private Rating rating;
    private String isbn;
    private String owner;
    
    /**
     * Instantiates a new Book object.
     * 
     * @param title the title of the book.
     * @param author the author of the book.
     * @param year the publishing year of the book.
     * @param rating the rating of the book.
     * @param isbn the ISBN of the book.
     * @param owner the owner of this book.
     * @throws IllegalArgumentException
     */
    public Book(String title, String author, int year, Rating rating,
            String isbn, String owner) throws IllegalArgumentException {
        super();
        this.setAuthor(author);
        this.setRating(rating);
        this.setTitle(title);
        this.setYear(year);
        this.setISBN(isbn);
        this.setOwner(owner);
    }
    
    /**
     * @param isbn the ISBN to be set.
     */
    private void setISBN(String isbn) throws IllegalArgumentException {
        if (! correctISBNFormat(isbn))
            throw new IllegalArgumentException("The ISBN did not have the correct format.");
        this.isbn = isbn;
    }
    
    /**
     * The ISBN is of one the following formats :
     * <ul>
     *      <li>0123456789</li>
     *      <li>123-0123456789</li>
     * </ul>
     * @param isbn the ISBN to be checked.
     * @return whether or not the ISBN value has the correct format.
     */
    public static boolean correctISBNFormat(String isbn) {
        String regex = "\\d+";
        if (isbn.length() == 10) return isbn.matches(regex);
        
        if (isbn.length() == 14) {
            String i[] = isbn.split("\\-");
            if (i[0].length() != 3) return false;
            return i[0].matches(regex) && correctISBNFormat(i[1]);
        }
        
        return false;
    }
    
    /**
     * @param author the author of the item.
     * @throws IllegalArgumentException 
     */
    private void setAuthor(String author) throws IllegalArgumentException {
        if (author.isEmpty())
            throw new IllegalArgumentException("The length of the author"
                    + " name must be greater than zero.");
        this.author = author;
    }

    /**
     * @param owner 
     * @throws IllegalArgumentException
     */
    private void setOwner(String owner) {
        if (author.isEmpty())
            throw new IllegalArgumentException("The length of the author"
                    + " name must be greater than zero.");
        this.owner = owner;
    }

    /**
     * @param rating the rating of the item.
     */
    private void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * @param title the title of the item.
     * @throws IllegalArgumentException 
     */
    private void setTitle(String title) throws IllegalArgumentException {
        if (title.isEmpty())
            throw new IllegalArgumentException("The length of the title"
                    + " must be greater than zero.");
        this.title = title;
    }

    /**
     * @param year the publishing year of the book.
     */
    private void setYear(int year) {
        this.year = year;
    }
    
    /**
     * @return the author of this book.
     */
    public String getAuthor() {
        return this.author;
    }
    
    /**
     * @return the rating of this book.
     */
    public Rating getRating() {
        return this.rating;
    }
    
    /**
     * @return the title of this book.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the publishing year of this book.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return the ISBN of the book.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * @return the name of the owner of this book.
     */
    public String getOwner() {
        return owner;
    }
    
    @Override
    public String toString() {
        return this.getAuthor() + ", " + this.getYear() + ", \"" + this.getTitle() + "\"";
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.getISBN());
        hash = 83 * hash + Objects.hashCode(this.getOwner());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Book) {
            Book i = (Book) obj;
            if (! this.getISBN().equals(i.getISBN()))
                return false;
            return this.getOwner().equalsIgnoreCase(i.getOwner());
        }
        return false;
    }
}
