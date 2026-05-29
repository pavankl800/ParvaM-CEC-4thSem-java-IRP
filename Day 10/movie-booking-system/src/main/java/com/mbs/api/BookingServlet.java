package com.mbs.api;

import com.google.gson.JsonObject;
import com.mbs.model.Booking;
import com.mbs.model.Movie;
import com.mbs.model.User;
import com.mbs.repository.BookingRepository;
import com.mbs.repository.MovieRepository;
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
 * Booking API
 *
 * GET    /api/bookings                      – list all bookings
 * GET    /api/bookings/{id}                 – get one booking
 * GET    /api/bookings?userId={userId}      – list bookings for a user
 * POST   /api/bookings                      – create booking (deducts wallet)
 * PUT    /api/bookings/{id}/cancel          – cancel booking (refunds wallet)
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/api/bookings", "/api/bookings/*"})
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        // GET /api/bookings/{id}
        if (pathInfo != null && !pathInfo.equals("/") && !pathInfo.endsWith("/cancel")) {
            String id = pathInfo.substring(1);
            Booking booking = BookingRepository.findById(id);
            if (booking != null) {
                JsonUtil.ok(res, booking);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Booking not found");
            }
            return;
        }

        // GET /api/bookings  or  GET /api/bookings?userId=...
        String userId = req.getParameter("userId");
        List<Booking> bookings = (userId != null && !userId.isBlank())
                ? BookingRepository.findByUser(userId)
                : BookingRepository.findAll();
        JsonUtil.ok(res, bookings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            JsonObject body = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), JsonObject.class);

            if (body == null
                    || !body.has("userId")
                    || !body.has("movieId")
                    || !body.has("seatsBooked")) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Required fields: userId, movieId, seatsBooked");
                return;
            }

            String userId    = body.get("userId").getAsString();
            String movieId   = body.get("movieId").getAsString();
            int    seats     = body.get("seatsBooked").getAsInt();

            if (seats <= 0) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "seatsBooked must be at least 1");
                return;
            }

            // Validate user
            User user = UserRepository.findById(userId);
            if (user == null) {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }

            // Validate movie
            Movie movie = MovieRepository.findById(movieId);
            if (movie == null) {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
                return;
            }

            // Check seat availability
            if (movie.getAvailableSeats() < seats) {
                JsonUtil.error(res, HttpServletResponse.SC_CONFLICT,
                        "Not enough seats available. Available: " + movie.getAvailableSeats());
                return;
            }

            double totalAmount = movie.getTicketPrice() * seats;

            // Check wallet balance
            if (user.getWalletBalance() < totalAmount) {
                JsonUtil.error(res, HttpServletResponse.SC_PAYMENT_REQUIRED,
                        "Insufficient wallet balance. Required: " + totalAmount
                        + ", Available: " + user.getWalletBalance());
                return;
            }

            Booking booking = BookingRepository.create(userId, movieId, seats, totalAmount);
            if (booking != null) {
                JsonUtil.created(res, booking);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Booking failed. Please try again.");
            }

        } catch (Exception e) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // e.g. /abc123/cancel

        if (pathInfo == null || !pathInfo.endsWith("/cancel")) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                    "Use PUT /api/bookings/{id}/cancel to cancel a booking");
            return;
        }

        // Extract booking id: strip leading "/" and trailing "/cancel"
        String bookingId = pathInfo.substring(1, pathInfo.lastIndexOf("/cancel"));

        if (bookingId.isBlank()) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Booking ID required");
            return;
        }

        Booking existing = BookingRepository.findById(bookingId);
        if (existing == null) {
            JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Booking not found");
            return;
        }

        if ("CANCELLED".equals(existing.getStatus())) {
            JsonUtil.error(res, HttpServletResponse.SC_CONFLICT, "Booking is already cancelled");
            return;
        }

        Booking cancelled = BookingRepository.cancel(bookingId);
        if (cancelled != null) {
            JsonUtil.ok(res, cancelled);
        } else {
            JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Cancellation failed. Please try again.");
        }
    }
}
