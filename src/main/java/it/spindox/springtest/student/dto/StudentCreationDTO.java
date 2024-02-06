package it.spindox.springtest.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class StudentCreationDTO {
    @NotEmpty(message = "Inserire un nome valido.")
    private String firstName;
    @NotEmpty(message = "Inserire un cognome valido.")
    private String lastName;
    @Email(message = "Inserire un'email valida.")
    @NotEmpty(message = "Inserire una email.")
    private String email;

    public StudentCreationDTO(String firstName, String lastName, String email) {
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
