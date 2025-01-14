package com.itsc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/addemployee")
public class AddEmployee extends HttpServlet {
    private static final String INSERT_QUERY = "INSERT INTO employeelist (name, designation, salary) VALUES (?, ?, ?)";

    private static final long serialVersionUID = 1L;

    public AddEmployee() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<link rel='stylesheet' href='css/bootstrap.min.css'>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/newemployee";
            String username = "root";
            String password = "DP9B8xE9%6ibZ.p";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            createEmployeeTable(conn);

            response.setContentType("text/html");

            String name = request.getParameter("name");
            String designation = request.getParameter("designation");
            int salary = Integer.parseInt(request.getParameter("salary"));

            PreparedStatement ps = conn.prepareStatement(INSERT_QUERY);
            ps.setString(1, name);
            ps.setString(2, designation);
            ps.setInt(3, salary);
            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<div class='alert alert-success'>");
                pw.println("<strong>Success!</strong> Employee registered successfully.");
                pw.println("</div>");
            } else {
                pw.println("<div class='alert alert-danger'>");
                pw.println("<strong>Error!</strong> Employee not registered.");
                pw.println("</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<div class='text-center mt-3'>");
        pw.println("<a href='landingpage.html' class='btn btn-secondary m-2'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='employeelist' class='btn btn-primary m-2'>Employee List</a>");
        pw.println("</div>");
    }

    private void createEmployeeTable(Connection conn) throws SQLException {
        String createEmployeeQuery = "CREATE TABLE IF NOT EXISTS employeelist (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "designation VARCHAR(255) NOT NULL," +
                "salary INT NOT NULL" +
                ")";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(createEmployeeQuery);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
