package com.example.storeoflove.requests.base;

import javax.servlet.http.HttpServlet;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {

    public abstract List<String> requiredParams();
}
