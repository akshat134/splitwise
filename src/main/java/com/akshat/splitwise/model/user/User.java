package com.akshat.splitwise.model.user;
import java.util.Objects;

public class User {
    private final Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public User(Long id, String name, String email, String phoneNumber) {
        validateId(id);
        validateName(name);
        if(isBothNullOrBlank(email, phoneNumber)) {
            throw new IllegalArgumentException("User must enter enter either phone number or email");
        }
        if(email != null) {
            validateEmail(email);
        }
        if(phoneNumber != null) {
            validatePhone(phoneNumber);
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateName(String name) {
        validateName(name);
        this.name = name;
    }

    public void updateEmail(String email) {
        if(isBothNullOrBlank(email, this.phoneNumber)) {
            throw new IllegalArgumentException("User must enter either email or phone number");
        }
        validateEmail(email);
        this.email = email;
    }

    public void updatePhone(String phoneNumber) {
        if(isBothNullOrBlank(this.email, phoneNumber)) {
            throw new IllegalArgumentException("User must enter either email or phone number");
        }
        validatePhone(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof User user))
            return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void validateId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    private void validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }

    private void validateEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if(!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePhone(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }
        if(!phoneNumber.matches("\\+?[1-9]\\d{7,14}")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private boolean isBothNullOrBlank(String a, String b) {
        return (a == null || a.isBlank()) && (b == null || b.isBlank());
    }
}
