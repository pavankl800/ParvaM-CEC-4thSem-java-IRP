package com.mbs.repository;

import com.mbs.model.Booking;
import com.mbs.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingRepository {

    // ------------------------------------------------------------------ CREATE (transactional)
    /**
     * Books seats for a user:
     *  1. Deduct wallet balance
     *  2. Decrease available seats
     *  3. Insert booking record
     * All three steps run inside a single transaction.
     */
    public static Booking create(String userId, String movieId, int seats, double totalAmount) {
        String bookingId = UUID.randomUUID().toString();
        String insertSql = "INSERT INTO bookings (id, user_id, movie_id, seats_booked, total_amount, status) " +
                           "VALUES (?, ?, ?, ?, ?, 'CONFIRMED')";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Deduct wallet
            boolean walletOk = UserRepository.deductWallet(conn, userId, totalAmount);
            if (!walletOk) {
                conn.rollback();
                return null; // insufficient balance – caller checks null
            }

            // 2. Decrease seats
            boolean seatsOk = MovieRepository.decreaseSeats(conn, movieId, seats);
            if (!seatsOk) {
                conn.rollback();
                return null; // not enough seats
            }

            // 3. Insert booking
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, bookingId);
                ps.setString(2, userId);
                ps.setString(3, movieId);
                ps.setInt   (4, seats);
                ps.setDouble(5, totalAmount);
                ps.executeUpdate();
            }

            conn.commit();
            return findById(bookingId);

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ CANCEL (transactional)
    /**
     * Cancels a booking:
     *  1. Mark booking as CANCELLED
     *  2. Refund wallet
     *  3. Restore available seats
     */
    public static Booking cancel(String bookingId) {
        String updateSql = "UPDATE bookings SET status = 'CANCELLED', cancelled_at = NOW() " +
                           "WHERE id = ? AND status = 'CONFIRMED'";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Fetch booking first (outside transaction lock is fine for read)
            Booking booking = findById(bookingId);
            if (booking == null || !"CONFIRMED".equals(booking.getStatus())) {
                conn.rollback();
                return null;
            }

            // 1. Mark cancelled
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, bookingId);
                int rows = ps.executeUpdate();
                if (rows == 0) { conn.rollback(); return null; }
            }

            // 2. Refund wallet
            UserRepository.refundWallet(conn, booking.getUserId(), booking.getTotalAmount());

            // 3. Restore seats
            MovieRepository.increaseSeats(conn, booking.getMovieId(), booking.getSeatsBooked());

            conn.commit();
            return findById(bookingId);

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ READ ALL
    public static List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = buildJoinQuery(null, null);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapFull(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ READ BY USER
    public static List<Booking> findByUser(String userId) {
        List<Booking> list = new ArrayList<>();
        String sql = buildJoinQuery("b.user_id = ?", null);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapFull(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ READ ONE
    public static Booking findById(String id) {
        String sql = buildJoinQuery("b.id = ?", null);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapFull(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ HELPERS
    private static String buildJoinQuery(String whereClause, String extra) {
        String sql = "SELECT b.*, u.name AS user_name, m.title AS movie_title, " +
                     "t.name AS theatre_name, m.show_time " +
                     "FROM bookings b " +
                     "JOIN users u    ON b.user_id  = u.id " +
                     "JOIN movies m   ON b.movie_id = m.id " +
                     "JOIN theatres t ON m.theatre_id = t.id ";
        if (whereClause != null) sql += "WHERE " + whereClause + " ";
        sql += "ORDER BY b.booked_at DESC";
        return sql;
    }

    private static Booking mapFull(ResultSet rs) throws SQLException {
        Booking b = new Booking(
            rs.getString("id"),
            rs.getString("user_id"),
            rs.getString("movie_id"),
            rs.getInt   ("seats_booked"),
            rs.getDouble("total_amount"),
            rs.getString("status"),
            rs.getString("booked_at"),
            rs.getString("cancelled_at")
        );
        b.setUserName   (rs.getString("user_name"));
        b.setMovieTitle (rs.getString("movie_title"));
        b.setTheatreName(rs.getString("theatre_name"));
        b.setShowTime   (rs.getString("show_time"));
        return b;
    }
}
