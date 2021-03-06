package com.codebinars.a2048game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
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
                    "FOREIGN KEY (" + COLUMN_USERNAME_ID + ") REFERENCES " + USER_TABLE + " (" + COLUMN_ID + ") )";

    protected static final String CREATE_USER_TABLE_STATEMENT =
            "CREATE TABLE " + USER_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_COUNTRY + " TEXT) ";

    private SQLiteDatabase db;

    private static DBHelper dbInstance = null;

    public static DBHelper getInstance(Context activityContext) {
        if (dbInstance == null) {
            dbInstance = new DBHelper(activityContext.getApplicationContext());
        }
        return dbInstance;
    }

    private DBHelper(Context applicationContext) {
        super(applicationContext, DB_NAME, null, 2);
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

    public void checkDbStatus() {
        if (db == null) {
            db = getWritableDatabase();
        }
    }

    /**
     * Method to add a Score in DB
     *
     * @param scoreModel Score to add
     */
    public void addScore(ScoreModel scoreModel) {
        checkDbStatus();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME_ID, scoreModel.getUsernameId());
        cv.put(COLUMN_SCORE, scoreModel.getScore());
        cv.put(COLUMN_DATETIME, scoreModel.getDatetime());
        cv.put(COLUMN_DURATION, scoreModel.getDuration());
        db.insert(SCORE_TABLE, null, cv);
    }


    /**
     * Get all scores in DB
     * Including Username, Country and Avatar of the USER matching that Score
     *Select id, number, date, name, country, avatarpath
     * FROM userTable
     * INNER JOIN scoreTable on id = user_id
     * @return ArrayList with all scores
     */
    public List<ScoreDisplay> getAllScores() {
        checkDbStatus();
        List<ScoreDisplay> getAllScores = new ArrayList<>();
        String queryAllScores =
                "SELECT "+ "SCORE."+COLUMN_ID+", "+COLUMN_SCORE+", "+COLUMN_DATETIME+", "+COLUMN_DURATION+", "+
                 COLUMN_USERNAME+", "+COLUMN_IMAGE+", "+COLUMN_COUNTRY +
                 " FROM "+USER_TABLE +" USER"+
                 " INNER JOIN "+SCORE_TABLE+ " SCORE" + " ON "+ "USER."+COLUMN_ID +" = "+COLUMN_USERNAME_ID;
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do{
            int scoreId = cursor.getInt(0);
            Integer score = cursor.getInt(1);
            String datetime = cursor.getString(2);
            Float duration = cursor.getFloat(3);
            String username = cursor.getString(4);
            String imagePath = cursor.getString(5);
            String country = cursor.getString(6);
            ScoreDisplay scoreDisplay = new ScoreDisplay(scoreId, score, datetime, duration, username, imagePath, country);
            getAllScores.add(scoreDisplay);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return getAllScores;
    }

    /**
     * Find top 10 scores
     * @return ArrayList10 with 10 top scores (Sorted by Score)
     */
    public List<ScoreDisplay> getTop10Scores() {
        checkDbStatus();
        List<ScoreDisplay> getAllScores = new ArrayList<>();
        String queryAllScores =
                 "SELECT "+ "SCORE."+COLUMN_ID+", "+COLUMN_SCORE+", "+COLUMN_DATETIME+", "+COLUMN_DURATION+", "+
                 COLUMN_USERNAME+", "+COLUMN_IMAGE+", "+COLUMN_COUNTRY +
                 " FROM "+USER_TABLE +" USER"+
                 " INNER JOIN "+SCORE_TABLE+ " SCORE" + " ON "+ "USER."+COLUMN_ID +" = "+COLUMN_USERNAME_ID +
                 " ORDER BY " + COLUMN_SCORE + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do{
                int scoreId = cursor.getInt(0);
                Integer score = cursor.getInt(1);
                String datetime = cursor.getString(2);
                Float duration = cursor.getFloat(3);
                String username = cursor.getString(4);
                String imagePath = cursor.getString(5);
                String country = cursor.getString(6);
                ScoreDisplay scoreDisplay = new ScoreDisplay(scoreId, score, datetime, duration, username, imagePath, country);
                getAllScores.add(scoreDisplay);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  getAllScores;
    }

    /**
     * Find top 10 scores by a determined user
     * @param usertop is the User we will use in query
     * @return ArrayList10 with 10 top scores (Sorted by Score with the specified username)
     */
    public List<ScoreDisplay> getTop10ByUser(String usertop) {
        checkDbStatus();
        List<ScoreDisplay> getAllScores = new ArrayList<>();
        String queryAllScores =
                "SELECT "+ "SCORE."+COLUMN_ID+", "+COLUMN_SCORE+", "+COLUMN_DATETIME+", "+COLUMN_DURATION+", "+
                        COLUMN_USERNAME+", "+COLUMN_IMAGE+", "+COLUMN_COUNTRY +
                        " FROM "+USER_TABLE +" USER"+
                        " INNER JOIN "+SCORE_TABLE+ " SCORE" + " ON "+ "USER."+COLUMN_ID +" = "+COLUMN_USERNAME_ID +
                        " WHERE " + COLUMN_USERNAME + " = '" + usertop +
                        "' ORDER BY " + COLUMN_SCORE + " DESC LIMIT 10";

        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do{
                int scoreId = cursor.getInt(0);
                Integer score = cursor.getInt(1);
                String datetime = cursor.getString(2);
                Float duration = cursor.getFloat(3);
                String username = cursor.getString(4);
                String imagePath = cursor.getString(5);
                String country = cursor.getString(6);
                ScoreDisplay scoreDisplay = new ScoreDisplay(scoreId, score, datetime, duration, username, imagePath, country);
                getAllScores.add(scoreDisplay);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  getAllScores;
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
     * If there's no topScore, return 20
     * @return topScore
     */
    public int getTopScore(){
        checkDbStatus();
        Integer topScore;
        String queryTopScore = "SELECT MAX(" + COLUMN_SCORE + ") FROM "+ SCORE_TABLE;
        Cursor cursor = db.rawQuery(queryTopScore, null);
        cursor.moveToFirst();
        topScore = cursor.getInt(0);
        if (topScore == 0 || topScore == null){
            return 20;
        }
        else{
            return topScore;
        }
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

    /**
     * UPDATE SCORE
     * @param id ID To update
     * @param username (We will get the new ID in case it's not registered in DB)
     * @param score Score to update
     * @param datetime Date to update
     * @param duration Duration to update
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
     * UPDATE USER
     * @param username Parameter of WHERE
     * @param avatar Avatar to update. If not specified, another query is executed
     * @param country Country to update
     */
    public void updateUser(String username, String avatar, String country){
        checkDbStatus();
        String updateUser;
        username = username.toLowerCase();
        if (avatar != null){
            updateUser =  "UPDATE " + USER_TABLE
                    + " SET " + COLUMN_COUNTRY + " = '" + country + "' , " +
                    COLUMN_IMAGE + " = '" + avatar +
                    "' WHERE " + COLUMN_USERNAME + " = '" + username + "'";}
        else{
            updateUser =  "UPDATE " + USER_TABLE
                    + " SET " + COLUMN_COUNTRY + " = '" + country +
                    "' WHERE " + COLUMN_USERNAME + " = '" + username + "'";
        }
        db.execSQL(updateUser);
    }
}
