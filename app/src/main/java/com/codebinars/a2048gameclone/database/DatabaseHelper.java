package com.codebinars.a2048gameclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String SCORE_TABLE = "SCORE_TABLE";
    private static final String DB_NAME = "Scores_db";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "NAME";
    private static final String COLUMN_SCORE = "SCORE";
    private static final String COLUMN_DATETIME = "DATETIME";
    private static final String COLUMN_DURATION = "DURATION";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCORE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_SCORE + " INT, " + COLUMN_DATETIME + " TEXT, " + COLUMN_DURATION + " INT) ";
        db.execSQL(createTableStatement);
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, scoreModel.getUsername());
        cv.put(COLUMN_SCORE, scoreModel.getScore());
        cv.put(COLUMN_DATETIME, scoreModel.getDatetime());
        cv.put(COLUMN_DURATION, scoreModel.getDuration());
        db.insert(SCORE_TABLE, null, cv);
    }

    /**
     * Get all scores in DB
     * @return ArrayList
     */
    public List<ScoreModel> getAllScores(){
        List<ScoreModel> getAllScores = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String queryAllScores = "SELECT * FROM " + SCORE_TABLE + " order by " + COLUMN_SCORE +" desc";
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, username, score, datetime, duration);
                getAllScores.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        db.close();
        return getAllScores;
    }

    /**
     * Find top 10 scores
     * @return ArrayList10
     */
    public List<ScoreModel> getTop10(){
        List<ScoreModel> getTop10 = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String queryTop10 = "SELECT * FROM " + SCORE_TABLE + " order by " + COLUMN_SCORE +" desc limit 10";
        Cursor cursor = db.rawQuery(queryTop10, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, username, score, datetime, duration);
                getTop10.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        db.close();
        return getTop10;
    }

    /**
     * Find top 10 scores by a determinated user
     * @param userTop is the User we will use in query
     * @return ArrayList10
     */
    public List<ScoreModel> getTop10ByUsername(String userTop){
        List<ScoreModel> getTop10ByUsername = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String queryTop10ByUsername = "SELECT * FROM " + SCORE_TABLE + " WHERE "+ COLUMN_USERNAME + " = '"+ userTop +"' order by " + COLUMN_SCORE +" desc limit 10";
        Cursor cursor = db.rawQuery(queryTop10ByUsername, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                Integer score = cursor.getInt(2);
                String datetime = cursor.getString(3);
                Float duration = cursor.getFloat(4);
                ScoreModel newScore = new ScoreModel(scoreId, username, score, datetime, duration);
                getTop10ByUsername.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        db.close();
        return getTop10ByUsername;
    }

    /**
     * We delete a Score by ID
     * Method used on RecyclerView
     * @param id to be deleted
     */
    public void deleteByID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryDeleteByID = "DELETE FROM " + SCORE_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        db.execSQL(queryDeleteByID);
        db.close();
        System.out.println("DELETE OF THE FOLLOWING ID WAS A SUCCESS: "+id);
    }

    /**
     * We search in DB the topScore
     * @return topScore
     */
    public int getTopScore(){
        SQLiteDatabase db = this.getWritableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();

        String updateScoreByID = "UPDATE " + SCORE_TABLE
                + " SET " + COLUMN_USERNAME + " = '" + username + "' , " +
                COLUMN_SCORE + " = " + score + ", " +
                COLUMN_DATETIME + " = '" + datetime + "', " +
                COLUMN_DURATION + " = " + duration +
                " WHERE " + COLUMN_ID + " = " + id;

        db.execSQL(updateScoreByID);
    }

    /**
     * Clean the DB. CARE !!
     */
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteAllQuery = "DELETE FROM "+ SCORE_TABLE;
        db.execSQL(deleteAllQuery);
    }
}

