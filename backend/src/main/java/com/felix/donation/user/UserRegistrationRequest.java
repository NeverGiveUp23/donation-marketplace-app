package com.felix.donation.user;

public record UserRegistrationRequest(String firstName, String lastName, String email, Integer age, String password, UserRole userRole) {
}
