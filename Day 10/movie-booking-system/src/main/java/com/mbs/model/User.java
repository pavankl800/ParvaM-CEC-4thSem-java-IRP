package com.mbs.model;

public class User {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String passwordHash;   // never serialised to client (see UserServlet)
    private double walletBalance;
    private String createdAt;
    private String updatedAt;

    public User() {}

    public User(String id, String name, String email, String phone,
                String passwordHash, double walletBalance,
                String createdAt, String updatedAt) {
        this.id            = id;
        this.name          = name;
        this.email         = email;
        this.phone         = phone;
        this.passwordHash  = passwordHash;
        this.walletBalance = walletBalance;
        this.createdAt     = createdAt;
        this.updatedAt     = updatedAt;
    }

    // Getters & Setters
    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }

    public String getName()                      { return name; }
    public void   setName(String v)              { this.name = v; }

    public String getEmail()                     { return email; }
    public void   setEmail(String v)             { this.email = v; }

    public String getPhone()                     { return phone; }
    public void   setPhone(String v)             { this.phone = v; }

    public String getPasswordHash()              { return passwordHash; }
    public void   setPasswordHash(String v)      { this.passwordHash = v; }

    public double getWalletBalance()             { return walletBalance; }
    public void   setWalletBalance(double v)     { this.walletBalance = v; }

    public String getCreatedAt()                 { return createdAt; }
    public void   setCreatedAt(String v)         { this.createdAt = v; }

    public String getUpdatedAt()                 { return updatedAt; }
    public void   setUpdatedAt(String v)         { this.updatedAt = v; }
}
