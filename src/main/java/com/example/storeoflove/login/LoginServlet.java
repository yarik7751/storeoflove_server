package com.example.storeoflove.login;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = "root";
        String password = "l8904070010384";
        String connectionUrl = "jdbc:mysql://localhost:3306/storeoflove";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    connectionUrl,
                    userName,
                    password
            );
            resp.getWriter().print("CONNECTED!");
        } catch (Exception error) {
            resp.getWriter().print("connection error");
        }
    }
}
