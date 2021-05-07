package com.example.storeoflove.requests.registration;

import com.example.storeoflove.models.DefaultResponse;
import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.tools.ResponseConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
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
        List<String> paramsNames = Collections.list(req.getParameterNames());

        if(requiredParams().size() >= 0) {
            if(paramsNames.size() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));
            }

            paramsNames.forEach(paramName -> {
                if (!requiredParams().contains(paramName)) {
                    try {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));
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

        String login = req.getParameter(PARAM_LOGIN);
        String userPassword = req.getParameter(PARAM_PASSWORD);
        String name = req.getParameter(PARAM_NAME);
        String lastName = req.getParameter(PARAM_LAST_NAME);
        String birthDate = req.getParameter(PARAM_BIRTH_DATE);
        String description = req.getParameter(PARAM_SHORT_DESCRIPTION);
        String gender = req.getParameter(PARAM_GENDER);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    connectionUrl,
                    userName,
                    password
            );

            Statement statement = connection.createStatement();
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
                    "'"+ login +"'," +
                    "'"+ userPassword +"'," +
                    "''," +
                    "'"+ name +"'," +
                    "'"+ lastName +"'," +
                    "'"+ description +"'," +
                    "'"+ birthDate +"'," +
                    "''," +
                    "'',gender);";
            statement.execute(sql);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_OK, ResponseConstants.OK));
        } catch (Exception error) {
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_SERVICE_UNAVAILABLE, ResponseConstants.WRONG_DB_CONNECTION));
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException throwables) {
                    resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_SERVICE_UNAVAILABLE, ResponseConstants.WRONG_DB_CONNECTION));
                }
            }
        }
    }
}
