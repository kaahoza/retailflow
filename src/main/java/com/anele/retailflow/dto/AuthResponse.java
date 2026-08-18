package com.anele.retailflow.dto;

public class AuthResponse {
    private Long customerId;
    private String name;
    private String email;
    private String token;

    public AuthResponse(Long customerId, String name, String email, String token) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public Long getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}