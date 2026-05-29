package com.mbs.repository;

import com.mbs.model.Theatre;
import com.mbs.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TheatreRepository {

    // ------------------------------------------------------------------ CREATE
    public static Theatre create(Theatre t) {
        String id  = UUID.randomUUID().toString();
        String sql = "INSERT INTO theatres (id, name, location, total_seats) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, t.getName());
            ps.setString(3, t.getLocation());
            ps.setInt   (4, t.getTotalSeats() > 0 ? t.getTotalSeats() : 100);

            if (ps.executeUpdate() > 0) {
                return findById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ READ ALL
    public static List<Theatre> findAll() {
        List<Theatre> list = new ArrayList<>();
        String sql = "SELECT * FROM theatres ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------------------------------------------------------ READ ONE
    public static Theatre findById(String id) {
        String sql = "SELECT * FROM theatres WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ UPDATE
    public static Theatre update(String id, Theatre t) {
        String sql = "UPDATE theatres SET name = ?, location = ?, total_seats = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getLocation());
            ps.setInt   (3, t.getTotalSeats() > 0 ? t.getTotalSeats() : 100);
            ps.setString(4, id);

            if (ps.executeUpdate() > 0) return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ DELETE
    public static boolean delete(String id) {
        String sql = "DELETE FROM theatres WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ------------------------------------------------------------------ MAPPER
    private static Theatre map(ResultSet rs) throws SQLException {
        return new Theatre(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("location"),
            rs.getInt   ("total_seats"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        );
    }
}
