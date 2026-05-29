package com.mbs.api;

import com.google.gson.JsonObject;
import com.mbs.model.User;
import com.mbs.repository.UserRepository;
import com.mbs.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * User CRUD API
 *
 * GET    /api/users          – list all users
 * GET    /api/users/{id}     – get one user
 * POST   /api/users          – create user
 * PUT    /api/users/{id}     – update user
 * DELETE /api/users/{id}     – delete user
 * PUT    /api/users/{id}/wallet – top-up wallet balance
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/api/users", "/api/users/*"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = JsonUtil.extractId(req);

        if (id == null) {
            List<User> users = UserRepository.findAll();
            // Strip password hashes before sending
            users.forEach(u -> u.setPasswordHash(null));
            JsonUtil.ok(res, users);
        } else {
            User user = UserRepository.findById(id);
            if (user != null) {
                user.setPasswordHash(null);
                JsonUtil.ok(res, user);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            User input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), User.class);

            if (input == null || input.getName() == null || input.getName().isBlank()
                    || input.getEmail() == null || input.getEmail().isBlank()
                    || input.getPhone() == null || input.getPhone().isBlank()
                    || input.getPasswordHash() == null || input.getPasswordHash().isBlank()) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Required fields: name, email, phone, passwordHash");
                return;
            }

            User created = UserRepository.create(input);
            if (created != null) {
                created.setPasswordHash(null);
                JsonUtil.created(res, created);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to create user (email may already exist)");
            }
        } catch (Exception e) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // e.g. /abc123 or /abc123/wallet

        if (pathInfo == null || pathInfo.equals("/")) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "User ID required in URL");
            return;
        }

        // Handle wallet top-up: PUT /api/users/{id}/wallet
        if (pathInfo.endsWith("/wallet")) {
            String userId = pathInfo.substring(1, pathInfo.lastIndexOf("/wallet"));
            handleWalletTopUp(req, res, userId);
            return;
        }

        // Normal update
        String id = JsonUtil.extractId(req);
        if (id == null) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "User ID required in URL");
            return;
        }

        try {
            User input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), User.class);

            if (input == null || input.getName() == null || input.getName().isBlank()
                    || input.getEmail() == null || input.getEmail().isBlank()
                    || input.getPhone() == null || input.getPhone().isBlank()) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Required fields: name, email, phone");
                return;
            }

            User updated = UserRepository.update(id, input);
            if (updated != null) {
                updated.setPasswordHash(null);
                JsonUtil.ok(res, updated);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = JsonUtil.extractId(req);
        if (id == null) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "User ID required in URL");
            return;
        }

        boolean deleted = UserRepository.delete(id);
        if (deleted) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

    // ------------------------------------------------------------------ wallet top-up
    private void handleWalletTopUp(HttpServletRequest req, HttpServletResponse res, String userId)
            throws IOException {

        User user = UserRepository.findById(userId);
        if (user == null) {
            JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        try {
            JsonObject body = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), JsonObject.class);
            if (body == null || !body.has("amount")) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Field 'amount' is required");
                return;
            }
            double amount = body.get("amount").getAsDouble();
            if (amount <= 0) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Amount must be positive");
                return;
            }

            user.setWalletBalance(user.getWalletBalance() + amount);
            User updated = UserRepository.update(userId, user);
            if (updated != null) {
                updated.setPasswordHash(null);
                JsonUtil.ok(res, updated);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Wallet update failed");
            }
        } catch (Exception e) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }
}
