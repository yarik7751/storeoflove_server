package com.example.storeoflove.requests.login;

import com.example.storeoflove.models.DefaultError;
import com.example.storeoflove.models.LoginResult;
import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.tools.ResponseConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends BaseServlet {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    @Override
    public List<String> requiredParams() {
        return Arrays.asList(PARAM_LOGIN, PARAM_PASSWORD);
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

        String login = req.getParameter(PARAM_LOGIN);
        String userPassword = req.getParameter(PARAM_PASSWORD);

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    connectionUrl,
                    userName,
                    password
            );

            Statement statement = connection.createStatement();
            ResultSet loginResult = statement.executeQuery("select * from people where email=\""+ login +"\" and `password`=\""+ userPassword +"\"");
            boolean isFirst = loginResult.next();
            if (isFirst) {
                String hash = generateToken();
                int id = loginResult.getInt("id");
                statement.execute("update people set token=\""+ hash +"\" where id="+ id);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().print(new LoginResult(hash));
            } else {
                resp.getWriter().print("empty result");
            }
        } catch (Exception error) {
            resp.getWriter().print("connection error");
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException throwables) { }
            }
        }
    }

    private String generateToken() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 50;

        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
