package com.codebinars.a2048game.database;

public class ScoreModel {
    private int id;
    private Integer usernameId;
    private Integer score;
    private String datetime;
    private Float duration;

    public ScoreModel(int id, Integer usernameId, Integer score, String datetime, Float duration) {
        this.id = id;
        this.usernameId = usernameId;
        this.score = score;
        this.datetime = datetime;
        this.duration = duration;
    }

    public ScoreModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(Integer usernameId) {
        this.usernameId = usernameId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", usernameId='" + usernameId + '\'' +
                ", score=" + score +
                ", datetime='" + datetime + '\'' +
                ", duration=" + duration +
                '}';
    }
}
