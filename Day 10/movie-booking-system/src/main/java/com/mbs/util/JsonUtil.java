package com.mbs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .serializeNulls()
            .create();

    public static Gson getGson() {
        return GSON;
    }

    /** Read the full request body as a String. */
    public static String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /** Write a JSON response with the given HTTP status. */
    public static void writeJson(HttpServletResponse response, int status, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(GSON.toJson(data));
        out.flush();
    }

    /** Convenience: 200 OK */
    public static void ok(HttpServletResponse response, Object data) throws IOException {
        writeJson(response, HttpServletResponse.SC_OK, data);
    }

    /** Convenience: 201 Created */
    public static void created(HttpServletResponse response, Object data) throws IOException {
        writeJson(response, HttpServletResponse.SC_CREATED, data);
    }

    /** Convenience: error response with message */
    public static void error(HttpServletResponse response, int status, String message) throws IOException {
        writeJson(response, status, new ErrorResponse(message));
    }

    /** Extract the last path segment after the servlet mapping, e.g. /api/theatres/{id} → id */
    public static String extractId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) return null;
        String trimmed = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
        // Only return if it looks like a single-segment id (no further slashes)
        if (trimmed.contains("/")) return null;
        return trimmed.isEmpty() ? null : trimmed;
    }

    // ---- inner helper ----
    public static class ErrorResponse {
        public final String error;
        public ErrorResponse(String error) { this.error = error; }
    }
}
