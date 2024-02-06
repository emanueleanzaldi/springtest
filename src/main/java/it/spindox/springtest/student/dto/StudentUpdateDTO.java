package it.spindox.springtest.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentUpdateDTO {
    @NotBlank(message = "Il nome non può essere vuoto.")
    private String firstName;
    @NotBlank(message = "Il cognome non può essere vuoto.")
    private String lastName;
    @Email(message = "Inserire una email valida.")
    @NotBlank(message = "Inserire una email valida.")
    private String email;

    public StudentUpdateDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
