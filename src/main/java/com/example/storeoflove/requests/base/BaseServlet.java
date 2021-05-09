package com.example.storeoflove.requests.base;

import com.example.storeoflove.models.DefaultResponse;
import com.example.storeoflove.tools.ResponseConstants;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {

    public abstract List<String> requiredParams();

    protected boolean checkRequestParams(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> paramsNames = Collections.list(req.getParameterNames());

        if(requiredParams().size() >= 0) {
            if(paramsNames.size() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));

                return false;
            }

            for (String paramName : paramsNames) {
                if (!requiredParams().contains(paramName)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().print(new DefaultResponse(HttpServletResponse.SC_BAD_REQUEST, ResponseConstants.WRONG_PARAMS));

                    return false;
                }
            }

            return true;
        }

        return true;
    }

    protected void connectToDatabase(HttpServletResponse resp, DataBaseConnection dataBaseConnection) throws IOException {
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

            Statement statement = connection.createStatement();
            dataBaseConnection.successful(statement);
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

    protected void sendResponseResult(HttpServletResponse resp, int code, Object response) throws IOException {
        Gson gson = new Gson();
        String jsonResult = gson.toJson(response);

        resp.setStatus(code);
        resp.getWriter().print(jsonResult);
    }

    protected void sendResponseResult(HttpServletResponse resp, Object response) throws IOException {
        sendResponseResult(resp, HttpServletResponse.SC_OK, response);
    }

    protected void sendResponseResult(HttpServletResponse resp, int code, String defaultMessage) throws IOException {
        resp.setStatus(code);
        resp.getWriter().print(new DefaultResponse(code, defaultMessage));
    }

    public interface DataBaseConnection {
        void successful(Statement statement) throws Exception;
    }
}
