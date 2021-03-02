package com.codebinars.a2048game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.codebinars.a2048game.scoresView.ScoreListRecycler;

import static com.codebinars.a2048game.scoresView.ScoreConstants.SCORE_USERNAME;

public class MainActivity extends Activity {

    private EditText playerusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent myIntent = null;
        switch (view.getId()) {
            case R.id.btnPlayGame:
                playerusername = findViewById(R.id.playerUsername);
                String username = playerusername.getText().toString();
                myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(SCORE_USERNAME, username);
                break;
            case R.id.btnCheckScore:
                myIntent = new Intent(this, ScoreListRecycler.class);
                break;
        }
        if(myIntent != null){
            startActivity(myIntent);
        }
    }
}