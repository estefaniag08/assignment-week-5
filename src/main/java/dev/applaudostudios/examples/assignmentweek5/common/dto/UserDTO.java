package dev.applaudostudios.examples.assignmentweek5.common.dto;

import dev.applaudostudios.examples.assignmentweek5.web.validation.PhoneNumberValid;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

public class UserDTO implements IEntityDTO {

    private Long id;

    @Email( message = "Invalid email format.")
    private String email;

    @NotEmpty(message = "Firstname is required.")
    @Size(max = 500, message = "Max length of the first name is 500.")
    private String firstName;

    @NotEmpty(message = "Lastname is required.")
    @Size(max = 500, message = "Max length of the last name is 500.")
    private String lastName;

    @NotEmpty(message = "Phone number is required.")
    @PhoneNumberValid(countryIndicator = "+507", numberLength = 8)
    private String phoneNumber;

    public UserDTO(){
        super();
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
        UserDTO user = (UserDTO) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .toString();
    }
}

