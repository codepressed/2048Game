package com.codebinars.a2048game.databaseRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ScoreDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void addScore(ScoreModel scoreModel);

    //Check if user exists. In case it doesn't, create it
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long addUser(UserModel userModel); // 1 = Created, 0 = Ignored

    //Delete query
    @Delete
    void delete(ScoreModel scoreModel);

    //Delete by id query
    @Query("DELETE FROM scores WHERE score_id = :scoreId")
    void deleteScoreByID(int scoreId);

    //Delete all query
    @Delete
    void deleteAllData(List<ScoreModel> scoreModelList, List<UserModel> userModelList);

    //Update user
    @Query("UPDATE users SET user_name = :uName, user_avatar = :uAvatar, user_country = :uCountry WHERE user_id = :uID")
    void updateUser(int uID, String uName, String uAvatar, String uCountry);

    //Update score
    @Query("UPDATE scores SET score_username_id = :sUsername, score_datetime = :sDateTime, score_duration = :sDuration WHERE score_id = :sID")
    void updateScore(int sID, String sUsername, String sDateTime, Float sDuration);

    //Select All
    @Query("SELECT * FROM scores ORDER BY score_value DESC")
    List<ScoreModel> getAllScores();

    //Top 10
    @Query("SELECT * FROM scores ORDER BY score_value DESC LIMIT 10")
    List<ScoreModel> getTop10Scores();

    //Top 10 by User
    @Query("SELECT * FROM scores WHERE score_username_id = :qUser ORDER BY score_value DESC")
    List<ScoreModel> getTop10ByUser(String qUser);

    //Top by Score
    @Query("SELECT MAX (score_value) FROM scores")
    int getTopScore();
}
