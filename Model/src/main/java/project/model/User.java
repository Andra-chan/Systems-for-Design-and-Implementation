package project.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


//@javax.persistence.Entity
//@Table( name = "Users", schema = "public")
public class User extends Entity<Integer> implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(){

    }

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

    //@Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //@Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //@Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //@Column(name = "password")
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
