package system;

import java.util.Objects;

/**
 * The User class models a user in the library application.
 * @author Joris Schelfaut
 */
public class User implements Comparable<User> {
    
    private String name;
    private String password;

    /**
     * @param name the name of the user.
     * @param password the password of the user.
     */
    public User(String name, String password) {
        super();
        this.setName(name);
        this.setPassword(password);
    }

    /**
     * @param name the name of the user.
     */
    private void setName(String name) {
        if (name.isEmpty())
            throw new IllegalArgumentException("The length of the author"
                    + " name must be greater than zero.");
        this.name = name;
    }

    /**
     * @param password password of the user.
     */
    private void setPassword(String password) {
        if (password.isEmpty())
            throw new IllegalArgumentException("The length of the author"
                    + " name must be greater than zero.");
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (! (obj instanceof User)) return false;
        User u = (User) obj;
        return u.getName().equalsIgnoreCase(this.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(User u) {
        // sort users by name :
        return this.getName().compareTo(u.getName());
    }

    @Override
    public String toString() {
        return this.getName() + "[" + this.getPassword() + "]";
    }
}
