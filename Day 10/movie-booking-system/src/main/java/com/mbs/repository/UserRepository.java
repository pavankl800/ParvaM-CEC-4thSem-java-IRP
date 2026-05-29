package com.mbs.repository;

import com.mbs.model.User;
import com.mbs.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    // ------------------------------------------------------------------ CREATE
    public static User create(User u) {
        String id  = UUID.randomUUID().toString();
        String sql = "INSERT INTO users (id, name, email, phone, password_hash, wallet_balance) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getPasswordHash());
            ps.setDouble(6, u.getWalletBalance() >= 0 ? u.getWalletBalance() : 0.0);

            if (ps.executeUpdate() > 0) return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ READ ALL
    public static List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";

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
    public static User findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";

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
    public static User update(String id, User u) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, wallet_balance = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
            ps.setDouble(4, u.getWalletBalance());
            ps.setString(5, id);

            if (ps.executeUpdate() > 0) return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------------------------------------ DELETE
    public static boolean delete(String id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ------------------------------------------------------------------ WALLET (transactional)
    /** Deduct amount from wallet; returns false if insufficient balance. */
    public static boolean deductWallet(Connection conn, String userId, double amount) throws SQLException {
        String sql = "UPDATE users SET wallet_balance = wallet_balance - ? " +
                     "WHERE id = ? AND wallet_balance >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, userId);
            ps.setDouble(3, amount);
            return ps.executeUpdate() > 0;
        }
    }

    /** Refund amount to wallet on cancellation. */
    public static void refundWallet(Connection conn, String userId, double amount) throws SQLException {
        String sql = "UPDATE users SET wallet_balance = wallet_balance + ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, userId);
            ps.executeUpdate();
        }
    }

    // ------------------------------------------------------------------ MAPPER
    private static User map(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("password_hash"),
            rs.getDouble("wallet_balance"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        );
    }
}
