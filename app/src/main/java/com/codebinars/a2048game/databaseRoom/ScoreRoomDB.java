package com.codebinars.a2048game.databaseRoom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScoreModel.class}, version = 2, exportSchema = false)
public abstract class ScoreRoomDB extends RoomDatabase {
    private static ScoreRoomDB database;
    public static String DATABASE_NAME = "scoreDB";

    public synchronized static ScoreRoomDB getInstance(Context context){
        //Check
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    ScoreRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract ScoreDao scoreDao();
}
