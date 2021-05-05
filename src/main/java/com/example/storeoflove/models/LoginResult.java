package com.example.storeoflove.models;

import com.google.gson.Gson;

public class LoginResult {

    private String token;

    public LoginResult(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
