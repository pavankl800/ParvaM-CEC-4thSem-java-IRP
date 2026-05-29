package com.mbs.model;

public class Movie {

    private String id;
    private String theatreId;
    private String title;
    private String genre;
    private String language;
    private int    durationMin;
    private String showTime;       // ISO-8601 string: "yyyy-MM-dd'T'HH:mm:ss"
    private double ticketPrice;
    private int    availableSeats;
    private String createdAt;
    private String updatedAt;

    // Optional: theatre name for display purposes (not stored in movies table)
    private String theatreName;

    public Movie() {}

    public Movie(String id, String theatreId, String title, String genre,
                 String language, int durationMin, String showTime,
                 double ticketPrice, int availableSeats,
                 String createdAt, String updatedAt) {
        this.id             = id;
        this.theatreId      = theatreId;
        this.title          = title;
        this.genre          = genre;
        this.language       = language;
        this.durationMin    = durationMin;
        this.showTime       = showTime;
        this.ticketPrice    = ticketPrice;
        this.availableSeats = availableSeats;
        this.createdAt      = createdAt;
        this.updatedAt      = updatedAt;
    }

    // Getters & Setters
    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }

    public String getTheatreId()                 { return theatreId; }
    public void   setTheatreId(String v)         { this.theatreId = v; }

    public String getTitle()                     { return title; }
    public void   setTitle(String v)             { this.title = v; }

    public String getGenre()                     { return genre; }
    public void   setGenre(String v)             { this.genre = v; }

    public String getLanguage()                  { return language; }
    public void   setLanguage(String v)          { this.language = v; }

    public int    getDurationMin()               { return durationMin; }
    public void   setDurationMin(int v)          { this.durationMin = v; }

    public String getShowTime()                  { return showTime; }
    public void   setShowTime(String v)          { this.showTime = v; }

    public double getTicketPrice()               { return ticketPrice; }
    public void   setTicketPrice(double v)       { this.ticketPrice = v; }

    public int    getAvailableSeats()            { return availableSeats; }
    public void   setAvailableSeats(int v)       { this.availableSeats = v; }

    public String getCreatedAt()                 { return createdAt; }
    public void   setCreatedAt(String v)         { this.createdAt = v; }

    public String getUpdatedAt()                 { return updatedAt; }
    public void   setUpdatedAt(String v)         { this.updatedAt = v; }

    public String getTheatreName()               { return theatreName; }
    public void   setTheatreName(String v)       { this.theatreName = v; }
}
