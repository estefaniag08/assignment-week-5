package dev.applaudostudios.examples.assignmentweek5.persistence.model;

import dev.applaudostudios.examples.assignmentweek5.web.validation.PhoneNumberValid;

import javax.persistence.*;

import javax.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "UserEntity")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Email( message = "Invalid email format.")
    @NotNull( message = "Email is required.")
    private String email;

    @NotEmpty(message = "Firstname is required.")
    @Size(max = 500, message = "Max length of the first name is 500.")
    private String firstName;

    @NotEmpty(message = "Lastname is required.")
    @Size(max = 500, message = "Max length of the last name is 500.")
    private String lastName;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    public User(){
    }

    public User(String email, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
