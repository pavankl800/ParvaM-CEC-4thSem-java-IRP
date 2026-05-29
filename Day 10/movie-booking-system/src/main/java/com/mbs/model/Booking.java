package com.mbs.model;

public class Booking {

    private String id;
    private String userId;
    private String movieId;
    private int    seatsBooked;
    private double totalAmount;
    private String status;       // CONFIRMED | CANCELLED
    private String bookedAt;
    private String cancelledAt;

    // Display helpers (joined from other tables)
    private String userName;
    private String movieTitle;
    private String theatreName;
    private String showTime;

    public Booking() {}

    public Booking(String id, String userId, String movieId, int seatsBooked,
                   double totalAmount, String status, String bookedAt, String cancelledAt) {
        this.id          = id;
        this.userId      = userId;
        this.movieId     = movieId;
        this.seatsBooked = seatsBooked;
        this.totalAmount = totalAmount;
        this.status      = status;
        this.bookedAt    = bookedAt;
        this.cancelledAt = cancelledAt;
    }

    // Getters & Setters
    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }

    public String getUserId()                    { return userId; }
    public void   setUserId(String v)            { this.userId = v; }

    public String getMovieId()                   { return movieId; }
    public void   setMovieId(String v)           { this.movieId = v; }

    public int    getSeatsBooked()               { return seatsBooked; }
    public void   setSeatsBooked(int v)          { this.seatsBooked = v; }

    public double getTotalAmount()               { return totalAmount; }
    public void   setTotalAmount(double v)       { this.totalAmount = v; }

    public String getStatus()                    { return status; }
    public void   setStatus(String v)            { this.status = v; }

    public String getBookedAt()                  { return bookedAt; }
    public void   setBookedAt(String v)          { this.bookedAt = v; }

    public String getCancelledAt()               { return cancelledAt; }
    public void   setCancelledAt(String v)       { this.cancelledAt = v; }

    public String getUserName()                  { return userName; }
    public void   setUserName(String v)          { this.userName = v; }

    public String getMovieTitle()                { return movieTitle; }
    public void   setMovieTitle(String v)        { this.movieTitle = v; }

    public String getTheatreName()               { return theatreName; }
    public void   setTheatreName(String v)       { this.theatreName = v; }

    public String getShowTime()                  { return showTime; }
    public void   setShowTime(String v)          { this.showTime = v; }
}
