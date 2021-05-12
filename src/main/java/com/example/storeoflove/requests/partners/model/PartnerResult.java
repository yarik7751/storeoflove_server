package com.example.storeoflove.requests.partners.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartnerResult {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("gender")
    @Expose
    private int gender;

    @SerializedName("short_description")
    @Expose
    private String shortDescription;

    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    @SerializedName("photos")
    @Expose
    private List<AttachmentResult> photos;

    private String photoIds;

    private String videoId;

    @SerializedName("video")
    @Expose
    private AttachmentResult video;

    public PartnerResult(int id, String firstName, String lastName, int gender, String shortDescription, String birthDate, String photoIds, String videoId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.shortDescription = shortDescription;
        this.birthDate = birthDate;
        this.photoIds = photoIds;
        this.videoId = videoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<AttachmentResult> getPhotos() {
        return photos;
    }

    public void setPhotos(List<AttachmentResult> photos) {
        this.photos = photos;
    }

    public String getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String photoIds) {
        this.photoIds = photoIds;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public AttachmentResult getVideo() {
        return video;
    }

    public void setVideo(AttachmentResult video) {
        this.video = video;
    }
}
