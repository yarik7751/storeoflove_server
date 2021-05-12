package com.example.storeoflove.requests.partners.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentResult {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("url")
    @Expose
    private String url;

    public AttachmentResult(int id, String url) {
        this.id = id;
        this.url = url;
    }
}
