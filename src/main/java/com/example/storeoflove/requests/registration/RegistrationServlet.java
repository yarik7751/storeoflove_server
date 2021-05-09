package com.example.storeoflove.requests.registration;

import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.tools.ResponseConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_LAST_NAME = "last_name";
    private static final String PARAM_BIRTH_DATE = "birth_date";
    private static final String PARAM_SHORT_DESCRIPTION = "description";
    private static final String PARAM_GENDER = "gender";

    @Override
    public List<String> requiredParams() {
        return Arrays.asList(
                PARAM_LOGIN,
                PARAM_PASSWORD,
                PARAM_NAME,
                PARAM_LAST_NAME,
                PARAM_BIRTH_DATE,
                PARAM_SHORT_DESCRIPTION,
                PARAM_GENDER
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!checkRequestParams(req, resp)) return;

        String login = req.getParameter(PARAM_LOGIN);
        String userPassword = req.getParameter(PARAM_PASSWORD);
        String name = req.getParameter(PARAM_NAME);
        String lastName = req.getParameter(PARAM_LAST_NAME);
        String birthDate = req.getParameter(PARAM_BIRTH_DATE);
        String description = req.getParameter(PARAM_SHORT_DESCRIPTION);
        String gender = req.getParameter(PARAM_GENDER);

        connectToDatabase(resp, (statement) -> {
            String sql = "insert into people (" +
                    "email," +
                    "`password`," +
                    "token," +
                    "first_name," +
                    "last_name," +
                    "short_description," +
                    "birthdate," +
                    "video," +
                    "photos," +
                    "gender" +
                    ") values (" +
                    "'" + login + "'," +
                    "'" + userPassword + "'," +
                    "''," +
                    "'" + name + "'," +
                    "'" + lastName + "'," +
                    "'" + description + "'," +
                    "'" + birthDate + "'," +
                    "''," +
                    "'',gender);";
            statement.execute(sql);

            sendResponseResult(resp, HttpServletResponse.SC_OK, ResponseConstants.OK);
        });
    }
}
