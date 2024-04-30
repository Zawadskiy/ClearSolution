package org.example.javapracticaltestassignment.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import org.example.javapracticaltestassignment.annotation.MinAge;

import java.time.LocalDate;

public class UserPartialRequest {

    @Email
    private String email;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @MinAge
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
