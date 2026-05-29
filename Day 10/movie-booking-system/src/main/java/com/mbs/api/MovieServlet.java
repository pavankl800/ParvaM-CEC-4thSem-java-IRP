package com.mbs.api;

import com.mbs.model.Movie;
import com.mbs.repository.MovieRepository;
import com.mbs.repository.TheatreRepository;
import com.mbs.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Movie CRUD API  (movies are always scoped to a theatre)
 *
 * GET    /api/movies                        – list all movies
 * GET    /api/movies/{id}                   – get one movie
 * GET    /api/movies?theatreId={theatreId}  – list movies for a theatre
 * POST   /api/movies                        – create movie (theatreId in body)
 * PUT    /api/movies/{id}                   – update movie
 * DELETE /api/movies/{id}                   – delete movie
 */
@WebServlet(name = "MovieServlet", urlPatterns = {"/api/movies", "/api/movies/*"})
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = JsonUtil.extractId(req);

        if (id == null) {
            String theatreId = req.getParameter("theatreId");
            List<Movie> movies = (theatreId != null && !theatreId.isBlank())
                    ? MovieRepository.findByTheatre(theatreId)
                    : MovieRepository.findAll();
            JsonUtil.ok(res, movies);
        } else {
            Movie movie = MovieRepository.findById(id);
            if (movie != null) {
                JsonUtil.ok(res, movie);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Movie input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), Movie.class);

            if (!validateMovie(input)) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Required fields: theatreId, title, genre, durationMin, showTime, ticketPrice, availableSeats");
                return;
            }

            // Verify theatre exists
            if (TheatreRepository.findById(input.getTheatreId()) == null) {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND,
                        "Theatre not found: " + input.getTheatreId());
                return;
            }

            Movie created = MovieRepository.create(input);
            if (created != null) {
                JsonUtil.created(res, created);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to create movie");
            }
        } catch (Exception e) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = JsonUtil.extractId(req);
        if (id == null) {
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Movie ID required in URL");
            return;
        }

        try {
            Movie input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), Movie.class);

            if (!validateMovie(input)) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Required fields: theatreId, title, genre, durationMin, showTime, ticketPrice, availableSeats");
                return;
            }

            // Verify theatre exists
            if (TheatreRepository.findById(input.getTheatreId()) == null) {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND,
                        "Theatre not found: " + input.getTheatreId());
                return;
            }

            Movie updated = MovieRepository.update(id, input);
            if (updated != null) {
                JsonUtil.ok(res, updated);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
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
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Movie ID required in URL");
            return;
        }

        boolean deleted = MovieRepository.delete(id);
        if (deleted) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
        }
    }

    // ------------------------------------------------------------------ validation
    private boolean validateMovie(Movie m) {
        return m != null
                && m.getTheatreId() != null && !m.getTheatreId().isBlank()
                && m.getTitle()     != null && !m.getTitle().isBlank()
                && m.getGenre()     != null && !m.getGenre().isBlank()
                && m.getShowTime()  != null && !m.getShowTime().isBlank()
                && m.getDurationMin() > 0
                && m.getTicketPrice() > 0
                && m.getAvailableSeats() >= 0;
    }
}
