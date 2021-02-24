package com.codebinars.a2048gameclone;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.codebinars.a2048gameclone", appContext.getPackageName());
    }

    @Test //Number 0: DELETE ALL RECORDS
    public void deleteData(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(appContext);
        databaseHelper.deleteAllData();

    }

    @Test //Number 1: Adding ScoreModels to DB: WORKS !!
    public void testAddingAndDisplayingDB(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(appContext);
         String name = "Eduardo Tequiero";
         int userScore = 5;
         String datetime = "03 20 4900";
         ScoreModel scoreModel = new ScoreModel();
         scoreModel.setUsername(name);
         scoreModel.setScore(userScore);
         scoreModel.setDatetime(datetime);
         databaseHelper.addScore(scoreModel);
         List<ScoreModel> scoreList;
         scoreList = databaseHelper.getTop10();
    }
}