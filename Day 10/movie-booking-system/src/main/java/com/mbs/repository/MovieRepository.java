package com.mbs.repository;

import com.mbs.model.Movie;
import com.mbs.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieRepository {

    // ------------------------------------------------------------------ CREATE
    public static Movie create(Movie m) {
        String id  = UUID.randomUUID().toString();
        String sql = "INSERT INTO movies (id, theatre_id, title, genre, language, " +
                     "duration_min, show_time, ticket_price, available_seats) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, m.getTheatreId());
            ps.setString(3, m.getTitle());
            ps.setString(4, m.getGenre());
            ps.setString(5, m.getLanguage() != null ? m.getLanguage() : "English");
            ps.setInt   (6, m.getDurationMin());
            ps.setString(7, m.getShowTime());
            ps.setDouble(8, m.getTicketPrice());
            ps.setInt   (9, m.getAvailableSeats());

            if (ps.executeUpdate() > 0) return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ READ ALL
    public static List<Movie> findAll() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT m.*, t.name AS theatre_name " +
                     "FROM movies m JOIN theatres t ON m.theatre_id = t.id " +
                     "ORDER BY m.show_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapWithTheatre(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ READ BY THEATRE
    public static List<Movie> findByTheatre(String theatreId) {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT m.*, t.name AS theatre_name " +
                     "FROM movies m JOIN theatres t ON m.theatre_id = t.id " +
                     "WHERE m.theatre_id = ? ORDER BY m.show_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, theatreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapWithTheatre(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ READ ONE
    public static Movie findById(String id) {
        String sql = "SELECT m.*, t.name AS theatre_name " +
                     "FROM movies m JOIN theatres t ON m.theatre_id = t.id " +
                     "WHERE m.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapWithTheatre(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ UPDATE
    public static Movie update(String id, Movie m) {
        String sql = "UPDATE movies SET theatre_id = ?, title = ?, genre = ?, language = ?, " +
                     "duration_min = ?, show_time = ?, ticket_price = ?, available_seats = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTheatreId());
            ps.setString(2, m.getTitle());
            ps.setString(3, m.getGenre());
            ps.setString(4, m.getLanguage() != null ? m.getLanguage() : "English");
            ps.setInt   (5, m.getDurationMin());
            ps.setString(6, m.getShowTime());
            ps.setDouble(7, m.getTicketPrice());
            ps.setInt   (8, m.getAvailableSeats());
            ps.setString(9, id);

            if (ps.executeUpdate() > 0) return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ DELETE
    public static boolean delete(String id) {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ------------------------------------------------------------------ SEAT MANAGEMENT
    /** Decrease available seats atomically; returns false if not enough seats. */
    public static boolean decreaseSeats(Connection conn, String movieId, int count) throws SQLException {
        String sql = "UPDATE movies SET available_seats = available_seats - ? " +
                     "WHERE id = ? AND available_seats >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt   (1, count);
            ps.setString(2, movieId);
            ps.setInt   (3, count);
            return ps.executeUpdate() > 0;
        }
    }

    /** Increase available seats on cancellation. */
    public static void increaseSeats(Connection conn, String movieId, int count) throws SQLException {
        String sql = "UPDATE movies SET available_seats = available_seats + ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt   (1, count);
            ps.setString(2, movieId);
            ps.executeUpdate();
        }
    }

    // ------------------------------------------------------------------ MAPPER
    private static Movie mapWithTheatre(ResultSet rs) throws SQLException {
        Movie m = new Movie(
            rs.getString("id"),
            rs.getString("theatre_id"),
            rs.getString("title"),
            rs.getString("genre"),
            rs.getString("language"),
            rs.getInt   ("duration_min"),
            rs.getString("show_time"),
            rs.getDouble("ticket_price"),
            rs.getInt   ("available_seats"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        );
        try { m.setTheatreName(rs.getString("theatre_name")); } catch (SQLException ignored) {}
        return m;
    }
}
