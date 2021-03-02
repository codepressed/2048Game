package com.codebinars.a2048game.databaseRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ScoreDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void addScore(ScoreModel scoreModel);

    //Delete query
    @Delete
    void delete(ScoreModel scoreModel);

    //Delete by id query
    @Query("DELETE FROM scores WHERE score_id = :scoreId")
    void deleteById(int scoreId);

    //Delete all query
    @Delete
    void deleteAllData(List<ScoreModel> scoreModelList);

    //Update query
    @Query("UPDATE scores SET score_username = :sUsername, score_datetime = :sDateTime, score_duration = :sDuration WHERE score_id = :sID")
    void updateScore(int sID, String sUsername, String sDateTime, Float sDuration);

    //Select All
    @Query("SELECT * FROM scores ORDER BY score_value DESC")
    List<ScoreModel> getAllScores();

    //Top 10
    @Query("SELECT * FROM scores ORDER BY score_value DESC LIMIT 10")
    List<ScoreModel> getTop10();

    //Top 10 by User
    @Query("SELECT * FROM scores WHERE score_value = :qUser ORDER BY score_value DESC LIMIT 10")
    List<ScoreModel> getTop10ByUsername(String qUser);

    //Top by Score
    @Query("SELECT MAX (score_value) FROM scores")
    int getTopScore();

}
