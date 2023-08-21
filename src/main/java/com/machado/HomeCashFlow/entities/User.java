package com.machado.HomeCashFlow.entities;

public class User {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String username;

    public User(String firstname, String lastname, String password, String email, String username) {
        if (firstname != null && !firstname.equals("")
                && lastname != null && !lastname.equals("")
                && password != null && !password.equals("")
                && email != null && !email.equals("")
                && username != null && !username.equals("")
                && password.length() >= 6
        ) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
            this.email = email;
            this.username = username;
        }

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
