package ui;

import args.Book;
import args.Rating;
import args.Session;
import exceptions.AuthenticationException;
import exceptions.AuthorizationException;
import exceptions.DuplicateException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import remote.IRemoteLibraryModule;
import remote.IRemoteSessionModule;

/**
 * The main view for the library application's text-based user interface.
 * A text-based user interface for the library application. The ApplicationView
 class provides methods for reading input from the console.
 *
 * @author Joris Schelfaut, Wouter Moermans, Prince Ansong, Bart Horre
 */
public class ApplicationView {
    
    private final IRemoteLibraryModule libraryModule;
    private final IRemoteSessionModule sessionModule;
    private Session session;
    private final Scanner scanner;
    private static final String TRY_AGAIN_MSG = "Wrong input, please try again.";

    /**
     * Instantiates a new text-based user interface.
     * @param irsm the remote session module which manages the session of the user.
     * @param irlm the remote library module which provides a public interface to interact with the library.
     */
    public ApplicationView(IRemoteSessionModule irsm, IRemoteLibraryModule irlm) {
        super();
        this.sessionModule = irsm;
        this.libraryModule = irlm;
        this.scanner = new Scanner(System.in);
        this.startUI();
    }

    /**
     * @return a remote object that implements the IRemoteLibraryModule interface.
     */
    IRemoteLibraryModule getLibraryModule() {
        return libraryModule;
    }

    /**
     * @return a remote object that implements the IRemoteSessionModule interface.
     */
    IRemoteSessionModule getSessionModule() {
        return sessionModule;
    }

    /**
     * @return the session of the logged in user.
     */
    Session getSession() {
        return session;
    }
    
    private void startUI() {
        System.out.println("#######################################");
        System.out.println("# Welcome to the library application! #");
        System.out.println("#######################################");
        
        showStartMenu();
    }
    
    /**
     * This menu is shown when not logged in. It is the default menu.
     */
    private void showStartMenu() {
        System.out.println("---------------------------------------");
        System.out.println("- Please select an option :           -");
        System.out.println("- 1. Login.                           -");
        System.out.println("- 2. Exit.                            -");
        System.out.println("---------------------------------------");
        
        int result = readInt(1, 2);
        switch (result) {
            case 1:
                showLoginMenu();
                break;
            case 2:
                showExitMessage();
                break;
        }
    }
    
    /**
     * Menu to login into the application.
     */
    private void showLoginMenu() {
        try {
            System.out.print("Username : ");
            String name = readString();
            System.out.print("Password : ");
            String password = readString();
            
            this.session = getSessionModule().authenticate(name, password);
            showMainMenu();
        } catch (AuthenticationException ae) {
            printException(ae);
            System.err.println("You entered a wrong combination of username and password.");
            showStartMenu();
        }
    }
    
    /**
     * Prints a goodbye message on the console.
     */
    private void showExitMessage() {
        System.out.println("Goodbye!");
    }
    
    /**
     * The main menu of the application.
     * This menu is only visible when logged in.
     */
    private void showMainMenu() {
        System.out.println("---------------------------------------");
        System.out.println("- Please select an option :           -");
        System.out.println("- 1. Library contents overview.       -");
        System.out.println("- 2. Add a new book.                  -");
        System.out.println("- 3. Stop the application.            -");
        System.out.println("---------------------------------------");

        int result = readInt(1, 3);
        switch (result) {
            case 1:
                showBooksOverview();
                break;
            case 2:
                showAddBookWizard();
                break;
            case 3:
                showExitMessage();
                break;
        }
    }

    /**
     * Prints an overview of all the books in the library.
     */
    private void showBooksOverview() {
        try {
            System.out.println("---------------------------------------");
            System.out.println("- Library books                       -");
            System.out.println("---------------------------------------");
            List<Book> books = getLibraryModule().getBooks();
            for (int i = 0; i < books.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + books.get(i));
            }
        } catch (RemoteException re) {
            printException(re);
        } finally {
            showMainMenu();
        }
    }

    /**
     * Prints a wizard and asks for user input to add a book to the library.
     */
    private void showAddBookWizard() {
        System.out.println("---------------------------------------");
        System.out.println("- New item wizard                     -");
        System.out.println("---------------------------------------");
        
        System.out.print("\tTitle : ");
        String title = readString();
        
        System.out.print("\tAuthor : ");
        String author = readString();
        
        System.out.print("\tPublication year : ");
        int year = readInt(0, Integer.MAX_VALUE);
        
        System.out.print("\tRating {1:POOR, 2:AVERAGE, 3:GOOD, 4:EXCELLENT, 5:UNKNOWN} : ");
        int value = readInt(1, 5);
        Rating[] values = Rating.values();
        Rating rating = values[value - 1];
        
        System.out.print("\tISBN : ");
        String isbn = readString();
        while (! Book.correctISBNFormat(isbn)) {
            System.err.print("\tWrong ISBN format, please try again : ");
            isbn = readString();
        }
        
        try {
            Book book = new Book(title, author, year, rating, isbn, getSession().getUsername());
            this.getLibraryModule().addBook(book, null);
            System.out.println("INFO : The item was successfully added to the library.");
        } catch (IllegalArgumentException | RemoteException | NullPointerException | DuplicateException | AuthorizationException ex) {
            printException(ex);
            System.err.println("INFO : The item was not added to the library.");
            showBooksOverview();
        }
    }

    /**
     * @param min the lowest possible expected input value.
     * @param max the highest possible expected input value.
     * @return the integer read from the console.
     */
    protected int readInt(int min, int max) {
        int result = min - 1;
        while (result < min || result > max) {
            try {
                result = scanner.nextInt();
                if (result > max || result < min) System.out.println(ApplicationView.TRY_AGAIN_MSG);
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println(ApplicationView.TRY_AGAIN_MSG);
                result = min - 1;
            }
        }
        return result;
    }

    /**
     * @param min the lowest possible expected input value.
     * @param max the highest possible expected input value.
     * @return the double value read from the console.
     */
    protected double readDouble(double min, double max) {
        double result = min - 1;
        while (result < min || result > max) {
            try {
                result = scanner.nextDouble();
                if (result > max || result < min) {
                    System.out.println("Wrong input, try again.");
                }
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println("Wrong input, try again.");
                result = min - 1;
            }
        }
        return result;
    }

    /**
     * @return the integer read from the console.
     */
    protected String readString() {
        String result = "";
        while (result.equals("")) {
            try {
                result = scanner.nextLine();
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println("Wrong input, try again.");
                result = "";
            }
        }
        return result;
    }

    /**
     * @return the boolean value of "y" or "n" read from the console.
     */
    protected boolean readYN() {
        String result = "";
        while (result.equals("")) {
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("y") || in.equalsIgnoreCase("n"))
                result = in;
            else System.out.println("Please type either 'y' or 'n'.");
        }
        return result.equals("y");
    }
    
    /**
     * @param ex the exception to be printed.
     */
    protected void printException(Exception ex) {
        System.err.println(ex.getClass().getName() + " : " + ex.getLocalizedMessage());
    }
}
