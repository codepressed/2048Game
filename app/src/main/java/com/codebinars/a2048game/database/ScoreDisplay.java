package com.codebinars.a2048game.database;

public class ScoreDisplay {
    int ID;
    Integer score;
    String username;
    String datetime;
    Float duration;
    String country;
    String avatar;

    public ScoreDisplay(int ID, Integer score, String datetime, Float duration, String username, String avatar, String country) {
        this.ID = ID;
        this.score = score;
        this.datetime = datetime;
        this.duration = duration;
        this.username = username;
        this.country = country;
        this.avatar = avatar;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
