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
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "NAME";
    private static final String COLUMN_SCORE = "SCORE";
    private static final String COLUMN_DATETIME = "DATETIME";



    public DatabaseHelper(@Nullable Context context) {
        super(context, "Scores.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCORE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_SCORE + " INT, " + COLUMN_DATETIME + " TEXT) ";
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
        cv.put(COLUMN_DATETIME, scoreModel.getDatatime());
        db.insert(SCORE_TABLE, null, cv);
    }


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
                String datatime = cursor.getString(3);
                ScoreModel newScore = new ScoreModel(scoreId, username, score, datatime);
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
        String queryTop10ByUsername = "SELECT * FROM " + SCORE_TABLE + " WHERE "+ COLUMN_USERNAME + " = "+ userTop +" order by " + COLUMN_SCORE +" desc limit 10";
        Cursor cursor = db.rawQuery(queryTop10ByUsername, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                Integer score = cursor.getInt(2);
                String datatime = cursor.getString(3);
                ScoreModel newScore = new ScoreModel(scoreId, username, score, datatime);
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
        db.rawQuery(queryDeleteByID, null);
    }

    /**
     * We search in DB the topScore
     * @return topScore
     */
    public int topScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        int topScore;
        String queryTopScore = "SELECT MAX(" + COLUMN_SCORE + ") topScore FROM "+ SCORE_TABLE;
        Cursor cursor = db.rawQuery(queryTopScore, null);
        cursor.moveToFirst();
        topScore = cursor.getInt(0);
        return topScore;
    }



}

