package nimor111.com.example.jersey.models;

import nimor111.com.example.jersey.validators.UserValidator;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class User {

    private UserValidator validators = new UserValidator();

    private String firstName;
    private String lastName;

    private String email;

    private String password;
    private Role role;

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
        if (this.validators.isEmailValid(email)) {
            this.email = email;
        } else {
            this.email = null;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
