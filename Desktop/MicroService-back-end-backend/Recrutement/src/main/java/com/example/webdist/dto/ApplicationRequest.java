package com.example.webdist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ApplicationRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String fullName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "L'URL du CV est obligatoire")
    private String cvUrl;

    // ✅ Getters
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    // ✅ Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }
}
