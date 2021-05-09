package com.example.storeoflove.requests.login;

import com.example.storeoflove.models.LoginResult;
import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.tools.ResponseConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Arrays;
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
        if(!checkRequestParams(req, resp)) return;

        String login = req.getParameter(PARAM_LOGIN);
        String userPassword = req.getParameter(PARAM_PASSWORD);

        connectToDatabase(resp, (statement) -> {
            ResultSet loginResult = statement.executeQuery("select * from people where email=\""+ login +"\" and `password`=\""+ userPassword +"\"");
            boolean isFirst = loginResult.next();
            if (isFirst) {
                String hash = generateToken();
                int id = loginResult.getInt("id");
                statement.execute("update people set token=\""+ hash +"\" where id="+ id);

                sendResponseResult(resp, new LoginResult(hash));
            } else {
                sendResponseResult(resp, HttpServletResponse.SC_SERVICE_UNAVAILABLE, ResponseConstants.WRONG_DB_CONNECTION);
            }
        });
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
