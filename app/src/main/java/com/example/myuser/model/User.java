package com.example.myuser.model;

import java.util.Arrays;

public class User {
    String name;
    String email;
    String location;
    byte[] image;

    public User() {
    }

    public User(String name, String email, String location, byte[] image) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
