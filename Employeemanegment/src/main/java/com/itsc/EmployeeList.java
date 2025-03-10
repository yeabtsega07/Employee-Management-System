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
import java.sql.ResultSet;

@WebServlet("/employeelist")
public class EmployeeList extends HttpServlet {
    private static final String SELECT_QUERY = "SELECT id, name, designation, salary FROM employeelist";
    private static final long serialVersionUID = 1L;

    public EmployeeList() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' href='css/bootstrap.min.css'>");

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

            response.setContentType("text/html");
            PreparedStatement ps = conn.prepareStatement(SELECT_QUERY);
            ResultSet rs = ps.executeQuery();

            out.println("<div class='container'>");
            out.println("<h1 class='text-center mt-5'>Employee List</h1>");

            out.println("<table class='table table-bordered table-responsive-md'>");
            out.println("<thead class='thead-dark'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Designation</th>");
            out.println("<th>Salary</th>");
            out.println("<th>Edit</th>");
            out.println("<th>Delete</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("designation") + "</td>");
                out.println("<td>" + rs.getInt("salary") + "</td>");

                out.println("<td>");
                out.println("<a class='btn btn-primary btn-sm' href='editScreen?id=" + rs.getInt("id") + "'>Edit</a>");
                out.println("</td>");

                out.println("<td>");
                out.println("<a class='btn btn-danger btn-sm' href='deleteurl?id=" + rs.getInt("id") + "'>Delete</a>");
                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");

            out.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>" + e.getMessage() + "</h1>");
        }

        out.println("<div class='text-center mt-3'>");
        out.println("<a href='landingpage.html' class='btn btn-secondary mr-2'>Home</a>");
        out.println("<a href='Addemployee.html' class='btn btn-primary'>Add Employee</a>");
        out.println("</div>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
