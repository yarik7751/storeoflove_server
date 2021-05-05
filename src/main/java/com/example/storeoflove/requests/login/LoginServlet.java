package com.example.storeoflove.requests.login;

import com.example.storeoflove.models.DefaultError;
import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.tools.ResponseConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends BaseServlet {

    @Override
    public List<String> requiredParams() {
        return Arrays.asList("login", "password");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<String> paramsNames = Collections.list(req.getParameterNames());

        if(requiredParams().size() >= 0) {
            if(paramsNames.size() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(new DefaultError(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));
            }

            paramsNames.forEach(paramName -> {
                if (!requiredParams().contains(paramName)) {
                    try {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().print(new DefaultError(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        String userName = "root";
        String password = "l8904070010384";
        String connectionUrl = "jdbc:mysql://localhost:3306/storeoflove";

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    connectionUrl,
                    userName,
                    password
            );

            //Statement statement = connection.createStatement();
            //statement.executeQuery("");
        } catch (Exception error) {
            resp.getWriter().print("connection error");
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException throwables) { }
            }
        }
    }
}
