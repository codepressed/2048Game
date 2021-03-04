package com.codebinars.a2048game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Scores_db";

    //USER TABLE
    protected static final String USER_TABLE = "USER_TABLE";
    protected static final String COLUMN_ID = "ID";
    protected static final String COLUMN_USERNAME = "USERNAME";
    protected static final String COLUMN_IMAGE = "IMAGE";
    protected static final String COLUMN_COUNTRY = "COUNTRY";

    //SCORE TABLE
    protected static final String SCORE_TABLE = "SCORE_TABLE";
    protected static final String COLUMN_USERNAME_ID = "USERNAME_ID";
    protected static final String COLUMN_SCORE = "SCORE";
    protected static final String COLUMN_DATETIME = "DATETIME";
    protected static final String COLUMN_DURATION = "DURATION";

    protected static final String CREATE_SCORE_TABLE_STATEMENT =
            "CREATE TABLE " + SCORE_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME_ID + " TEXT, " +
                    COLUMN_SCORE + " INTEGER, " +
                    COLUMN_DATETIME + " TEXT, " +
                    COLUMN_DURATION + " INTEGER, " +
                    "FOREIGN KEY ("+COLUMN_USERNAME_ID+") REFERENCES "+USER_TABLE+" ("+COLUMN_ID+") )";

    protected static final String CREATE_USER_TABLE_STATEMENT =
            "CREATE TABLE " + USER_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_COUNTRY + " TEXT) ";

    private SQLiteDatabase db;

    private static DatabaseHelper dbInstance = null;

    public static DatabaseHelper getInstance(Context activityContext){
        if(dbInstance == null){
                dbInstance = new DatabaseHelper(activityContext.getApplicationContext());
        }
        return dbInstance ;
        }


    private DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_STATEMENT);
        db.execSQL(CREATE_SCORE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + SCORE_TABLE);
        onCreate(db);
    }

    /**
     * Method to add a Score in DB
     * @param scoreModel Score to add
     */
    public void addScore(ScoreModel scoreModel){
        checkDbStatus();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME_ID, scoreModel.getUsernameId());
        cv.put(COLUMN_SCORE, scoreModel.getScore());
        cv.put(COLUMN_DATETIME, scoreModel.getDatetime());
        cv.put(COLUMN_DURATION, scoreModel.getDuration());
        long success = db.insert(SCORE_TABLE, null, cv);
        System.out.println(success);
    }

    /**
     * Get all scores in DB
     * @return ArrayList
     */
    public List<ScoreModel> getAllScores(){
        checkDbStatus();
        List<ScoreModel> getAllScores = new ArrayList<>();
        String queryAllScores = "SELECT * FROM " + SCORE_TABLE + " ORDER BY " + COLUMN_SCORE +" DESC";
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                Integer usernameId = cursor.getInt(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, usernameId, score, datetime, duration);
                getAllScores.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        return getAllScores;
    }

    /**
     * Find top 10 scores
     * @return ArrayList10
     */
    public List<ScoreModel> getTop10Score(){
        checkDbStatus();
        List<ScoreModel> getTop10 = new ArrayList<>();
        String queryTop10 = "SELECT * FROM " + SCORE_TABLE + " ORDER BY " + COLUMN_SCORE +" DESC LIMIT 10";
        Cursor cursor = db.rawQuery(queryTop10, null);
        if (cursor.moveToNext()) {
            do {
                Integer scoreId = cursor.getInt(0);
                Integer usernameId = cursor.getInt(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, usernameId, score, datetime, duration);
                getTop10.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        return getTop10;
    }

    /**
     * Find top 10 scores by a determined user
     * @param userTop is the User we will use in query
     * @return ArrayList10
     */
    public List<ScoreModel> getTop10ByUsername(String userTop){
        checkDbStatus();
        List<ScoreModel> getTop10ByUsername = new ArrayList<>();
        String queryTop10ByUsername = "SELECT * FROM " + SCORE_TABLE + " WHERE "+ COLUMN_USERNAME_ID + " = '"+ UserInDB(userTop) +"' ORDER BY " + COLUMN_SCORE +" DESC LIMIT 10";
        Cursor cursor = db.rawQuery(queryTop10ByUsername, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                Integer usernameId = cursor.getInt(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, usernameId, score, datetime, duration);
                getTop10ByUsername.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        return getTop10ByUsername;
    }

    /**
     * We delete a Score by ID
     * Method used on RecyclerView
     * @param id to be deleted
     */
    public void deleteScoreByID(Integer id){
        checkDbStatus();
        String queryDeleteByID = "DELETE FROM " + SCORE_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        db.execSQL(queryDeleteByID);
        }

    /**
     * We search in DB the topScore
     * @return topScore
     */
    public int getTopScore(){
        checkDbStatus();
        Integer topScore;
        String queryTopScore = "SELECT MAX(" + COLUMN_SCORE + ") FROM "+ SCORE_TABLE;
        Cursor cursor = db.rawQuery(queryTopScore, null);
        cursor.moveToFirst();
        topScore = cursor.getInt(0);
        if (topScore == null){
            return 20;
        }
        else{
        return topScore;
        }
    }

    /**
     * Update an item of DB
     */
    public void updateScore(int id, String username, Integer score, String datetime, Float duration){
        checkDbStatus();
        String updateScoreByID = "UPDATE " + SCORE_TABLE
                + " SET " + COLUMN_USERNAME_ID + " = '" + UserInDB(username) + "' , " +
                COLUMN_SCORE + " = " + score + ", " +
                COLUMN_DATETIME + " = '" + datetime + "', " +
                COLUMN_DURATION + " = " + duration +
                " WHERE " + COLUMN_ID + " = " + id;
        db.execSQL(updateScoreByID);
    }

    /**
     * Clean the DB
     */
    public void deleteAllData(){
        checkDbStatus();
        String deleteAllQuery = "DELETE FROM "+ SCORE_TABLE;
        db.execSQL(deleteAllQuery);
    }

    /**
     * Check if user exists in DB
     * If it doesn't, create it
     */
    public int UserInDB(String username){
        checkDbStatus();
        int userId;
        username = username.toLowerCase();
        String queryUserExist = "SELECT "+COLUMN_ID+" FROM "+ USER_TABLE + " WHERE " + COLUMN_USERNAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(queryUserExist, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            userId = cursor.getInt(0);
            System.out.println("USER WAS ALREADY CREATED: "+username + " with the following id: "+userId);
        }
        else{
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USERNAME, username);
            cv.put(COLUMN_COUNTRY, "Unknown country");
            db.insert(USER_TABLE, null, cv);
            String queryLastID = "SELECT MAX(" + COLUMN_ID + ") FROM "+USER_TABLE;
            cursor = db.rawQuery(queryLastID, null);
            cursor.moveToFirst();
            userId = cursor.getInt(0);

            System.out.println("USER HAS BEEN CREATED: "+username + " with the following id: "+ userId);
        }
        cursor.close();
        return userId;
    }

    public String getUser(Integer usernameId) {
        checkDbStatus();
        String queryGetUser = "SELECT "+ COLUMN_USERNAME +", "+COLUMN_ID+" FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + usernameId;
        Cursor cursor = db.rawQuery(queryGetUser, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getCountry(Integer usernameId) {
        checkDbStatus();
        String queryGetUser = "SELECT "+ COLUMN_COUNTRY +" FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + usernameId;
        Cursor cursor = db.rawQuery(queryGetUser, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public Bitmap getImage(Integer usernameId) {
        checkDbStatus();
        Bitmap theImage = null;
        String queryGetImage = "SELECT "+ COLUMN_IMAGE +" FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + usernameId;
        Cursor cursor = db.rawQuery(queryGetImage, null);
        if (cursor.moveToNext()){
            theImage = loadImage(cursor.getString(0));
        }
        return theImage;
    }

    public void updateUser(String username, String avatar, String country){
        checkDbStatus();
        username = username.toLowerCase();
        String updateUser =  "UPDATE " + USER_TABLE
                + " SET " + COLUMN_COUNTRY + " = '" + country + "' , " +
                            COLUMN_IMAGE + " = '" + avatar +
                "' WHERE " + COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(updateUser);
    }

    public void checkDbStatus(){
        if(db == null){
            db = getWritableDatabase();
        }
    }

    public Bitmap loadImage(String imageRoot){
        Bitmap bitmap = null;
        try {
            File file = new File (imageRoot);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (Exception e) {
           bitmap = null;
        }

        return bitmap;
    }
}



