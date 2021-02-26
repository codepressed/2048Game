package com.codebinars.a2048gameclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.engine.GameManager;
import com.codebinars.a2048gameclone.scoresView.ScoreListRecycler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
    }

    public void onClick(View view) {
        Intent myIntent = null;
        switch (view.getId()) {
            case R.id.btnPlayGame:
                myIntent = new Intent(MainActivity.this, GameActivity.class);
                break;
            case R.id.btnCheckScore:
                myIntent = new Intent(MainActivity.this, ScoreListRecycler.class);
                break;
        }
        if(myIntent != null){
            startActivity(myIntent);
        }
    }
}