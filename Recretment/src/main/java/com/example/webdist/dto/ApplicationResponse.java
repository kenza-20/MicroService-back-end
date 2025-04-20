package com.example.webdist.dto;

public class ApplicationResponse {
    private String fullName;
    private String email;
    private String cvUrl;

    // ✅ Constructeur avec tous les champs
    public ApplicationResponse(String fullName, String email, String cvUrl) {
        this.fullName = fullName;
        this.email = email;
        this.cvUrl = cvUrl;
    }

    // 🔄 Constructeur vide (important pour Spring)
    public ApplicationResponse() {
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    // Setters
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
