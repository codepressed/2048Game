package com.codebinars.a2048game.databaseRoom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "scores",
        foreignKeys = {
                @ForeignKey(entity = UserModel.class,
                            parentColumns = "user_id",
                            childColumns = "score_username_id")})
public class ScoreModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "score_id")
    protected String mId;

    @ColumnInfo(name = "score_value")
    protected Integer mScore;

    @ColumnInfo(name = "score_username_id")
    protected String mUsername;

    @ColumnInfo(name = "score_datetime")
    protected String mDateTime;

    @ColumnInfo(name = "score_duration")
    protected Float mDuration;

    @Ignore
    public ScoreModel(String username, Integer score, String dateTime, Float duration) {
        mId = UUID.randomUUID().toString();
        mScore = score;
        mUsername = username;
        mDateTime = dateTime;
        mDuration = duration;
    }

    public ScoreModel(String mId, Integer score, String mUsername, String mDateTime, Float mDuration) {
        this.mId = mId;
        this.mScore = score;
        this.mUsername = mUsername;
        this.mDateTime = mDateTime;
        this.mDuration = mDuration;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Integer getmScore() {
        return mScore;
    }

    public void setmScore(Integer mScore) {
        this.mScore = mScore;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(String mDateTime) {
        this.mDateTime = mDateTime;
    }

    public Float getmDuration() {
        return mDuration;
    }

    public void setmDuration(Float mDuration) {
        this.mDuration = mDuration;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "mId='" + mId + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mDateTime='" + mDateTime + '\'' +
                ", mDuration=" + mDuration +
                '}';
    }
}