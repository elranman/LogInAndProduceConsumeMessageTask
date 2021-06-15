package com.project.authentication.model;

public class User {
    private String fullName;
    private String emailAddress;
    private String password;
    private String personalToken;
    private String secret;

    public User(String fullName, String emailAddress, String password, String personalToken, String secret) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.personalToken = personalToken;
        this.secret = secret;
    }

    public String getPersonalToken() {
        return personalToken;
    }

    public void setPersonalToken(String personalToken) {
        this.personalToken = personalToken;
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
