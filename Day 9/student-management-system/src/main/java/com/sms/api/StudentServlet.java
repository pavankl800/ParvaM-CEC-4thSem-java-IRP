package com.sms.api;

import com.google.gson.Gson;
import com.sms.model.Student;
import com.sms.repository.StudentRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// API Endpoint
@WebServlet(name = "StudentServlet", urlPatterns = {"/api/students", "/api/students/*"})
public class StudentServlet extends HttpServlet {

    private Gson gson = new Gson();

    private String getPathId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        return pathInfo.substring(1);
    }
    // Helper Method to check the JSON format
    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        return jsonBuffer.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String id = getPathId(request);

        if (id == null) {
            // Get all students
            List<Student> students = StudentRepository.getAllStudents();
            out.print(gson.toJson(students));
        } else {
            // Get single student
            Student student = StudentRepository.getStudentById(id);
            if (student != null) {
                out.print(gson.toJson(student));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Student not found\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String jsonBody = getRequestBody(request);
            Student newStudent = gson.fromJson(jsonBody, Student.class);
            
            if (newStudent == null || newStudent.getName() == null || newStudent.getEmail() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\":\"Invalid student data\"}");
                return;
            }

            Student savedStudent = StudentRepository.addStudent(newStudent);
            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
            response.getWriter().print(gson.toJson(savedStudent));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Failed to parse JSON\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String id = getPathId(request);
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Student ID is required in the URL\"}");
            return;
        }

        try {
            String jsonBody = getRequestBody(request);
            Student updatedData = gson.fromJson(jsonBody, Student.class);

            if (updatedData == null || updatedData.getName() == null || updatedData.getEmail() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\":\"Invalid student data\"}");
                return;
            }

            Student updatedStudent = StudentRepository.updateStudent(id, updatedData);
            if (updatedStudent != null) {
                response.getWriter().print(gson.toJson(updatedStudent));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print("{\"error\":\"Student not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Failed to parse JSON\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String id = getPathId(request);
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Student ID is required in the URL\"}");
            return;
        }

        boolean deleted = StudentRepository.deleteStudent(id);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print("{\"error\":\"Student not found\"}");
        }
    }
}
