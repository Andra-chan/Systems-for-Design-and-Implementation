package project.model;

import java.io.Serializable;
import java.util.Objects;

public class User extends Entity<Integer> implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = "";
        this.password = "";
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * How a user will be showcased
     *
     * @return String
     */
    @Override
    public String toString() {
        return "User: " + this.getLastName() + " " + this.getFirstName();
    }

    /**
     * Equals - HashCode rule: if obj1.equals(obj2)
     * Then obj1.hashCode() == obj2.hashCode()
     */

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        User that = (User) o;
        return getId().equals(that.getId()) &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getClass());
    }
}
