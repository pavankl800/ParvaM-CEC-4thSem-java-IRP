package com.mbs.api;

import com.mbs.model.Theatre;
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
 * Theatre CRUD API
 *
 * GET    /api/theatres          – list all
 * GET    /api/theatres/{id}     – get one
 * POST   /api/theatres          – create
 * PUT    /api/theatres/{id}     – update
 * DELETE /api/theatres/{id}     – delete
 */
@WebServlet(name = "TheatreServlet", urlPatterns = {"/api/theatres", "/api/theatres/*"})
public class TheatreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = JsonUtil.extractId(req);

        if (id == null) {
            List<Theatre> theatres = TheatreRepository.findAll();
            JsonUtil.ok(res, theatres);
        } else {
            Theatre theatre = TheatreRepository.findById(id);
            if (theatre != null) {
                JsonUtil.ok(res, theatre);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Theatre not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Theatre input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), Theatre.class);

            if (input == null || input.getName() == null || input.getName().isBlank()
                    || input.getLocation() == null || input.getLocation().isBlank()) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Fields 'name' and 'location' are required");
                return;
            }

            Theatre created = TheatreRepository.create(input);
            if (created != null) {
                JsonUtil.created(res, created);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to create theatre");
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
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Theatre ID required in URL");
            return;
        }

        try {
            Theatre input = JsonUtil.getGson().fromJson(JsonUtil.readBody(req), Theatre.class);

            if (input == null || input.getName() == null || input.getName().isBlank()
                    || input.getLocation() == null || input.getLocation().isBlank()) {
                JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Fields 'name' and 'location' are required");
                return;
            }

            Theatre updated = TheatreRepository.update(id, input);
            if (updated != null) {
                JsonUtil.ok(res, updated);
            } else {
                JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Theatre not found");
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
            JsonUtil.error(res, HttpServletResponse.SC_BAD_REQUEST, "Theatre ID required in URL");
            return;
        }

        boolean deleted = TheatreRepository.delete(id);
        if (deleted) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            JsonUtil.error(res, HttpServletResponse.SC_NOT_FOUND, "Theatre not found");
        }
    }
}
