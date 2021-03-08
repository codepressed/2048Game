package com.codebinars.a2048game.databaseRoom;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.codebinars.a2048game.databaseRoom.*;

import java.util.UUID;

@Entity(tableName = "users")
public class UserModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    protected String uId;

    @ColumnInfo(name = "user_name")
    protected String uName;

    @ColumnInfo(name = "user_country")
    protected String uCountry;

    @ColumnInfo(name = "user_avatar")
    protected String uAvatarpath;

    @Ignore
    public UserModel(@NonNull String uId, String uName, String uCountry, String uAvatarpath) {
        this.uId = UUID.randomUUID().toString();
        this.uName = uName;
        this.uCountry = uCountry;
        this.uAvatarpath = uAvatarpath;
    }

    public UserModel(String uName, String uCountry, String uAvatarpath) {
        this.uName = uName;
        this.uCountry = uCountry;
        this.uAvatarpath = uAvatarpath;
    }

    @NonNull
    public String getuId() {
        return uId;
    }

    public void setuId(@NonNull String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuCountry() {
        return uCountry;
    }

    public void setuCountry(String uCountry) {
        this.uCountry = uCountry;
    }

    public String getuAvatarpath() {
        return uAvatarpath;
    }

    public void setuAvatarpath(String uAvatarpath) {
        this.uAvatarpath = uAvatarpath;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                ", uCountry='" + uCountry + '\'' +
                ", uAvatarpath='" + uAvatarpath + '\'' +
                '}';
    }
}
