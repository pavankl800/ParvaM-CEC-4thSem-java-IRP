package com.mbs.model;

public class Theatre {

    private String id;
    private String name;
    private String location;
    private int    totalSeats;
    private String createdAt;
    private String updatedAt;

    public Theatre() {}

    public Theatre(String id, String name, String location, int totalSeats,
                   String createdAt, String updatedAt) {
        this.id         = id;
        this.name       = name;
        this.location   = location;
        this.totalSeats = totalSeats;
        this.createdAt  = createdAt;
        this.updatedAt  = updatedAt;
    }

    // Getters & Setters
    public String getId()                    { return id; }
    public void   setId(String id)           { this.id = id; }

    public String getName()                  { return name; }
    public void   setName(String name)       { this.name = name; }

    public String getLocation()              { return location; }
    public void   setLocation(String loc)    { this.location = loc; }

    public int    getTotalSeats()            { return totalSeats; }
    public void   setTotalSeats(int seats)   { this.totalSeats = seats; }

    public String getCreatedAt()             { return createdAt; }
    public void   setCreatedAt(String v)     { this.createdAt = v; }

    public String getUpdatedAt()             { return updatedAt; }
    public void   setUpdatedAt(String v)     { this.updatedAt = v; }
}
