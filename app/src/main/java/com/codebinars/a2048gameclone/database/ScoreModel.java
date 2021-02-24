package com.codebinars.a2048gameclone.database;

public class ScoreModel {
    private int id;
    private String username;
    private Integer score;
    private String datetime;

    public ScoreModel(int id, String username, Integer score, String datetime) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.datetime = datetime;
    }

    public ScoreModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score='" + score + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
