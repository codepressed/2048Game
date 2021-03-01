package com.codebinars.a2048gameclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.scoresView.ScoreListRecycler;

public class EditScoreActivity extends Activity {
    private DatabaseHelper databaseHelper;
    private EditText editUsername, editDate, editScore, editDuration;
    private int scoreId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_score);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        editScore = findViewById(R.id.editScoreCamp);
        editUsername = findViewById(R.id.editUsernameCamp);
        editDate = findViewById(R.id.editDateCamp);
        editDuration = findViewById(R.id.editDurationCamp);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        scoreId = extras.getInt("Id_key");
        editScore.setText(extras.getString("Score_key"));
        editUsername.setText(extras.getString("Username_key"));
        editDate.setText(extras.getString("Date_key"));
        editDuration.setText(extras.getString("Duration_key"));
    }

    public void onClick(View view) {
        Intent myIntent = null;
        switch (view.getId()) {
            case R.id.savechanges:
                databaseHelper.updateScore(
                        scoreId,
                        editUsername.getText().toString(),
                        Integer.valueOf(editScore.getText().toString()),
                        editDate.getText().toString(),
                        Float.parseFloat(editDuration.getText().toString()));
                myIntent = new Intent(EditScoreActivity.this, ScoreListRecycler.class);
                break;
            case R.id.undochanges:
                myIntent = new Intent(EditScoreActivity.this, ScoreListRecycler.class);
                break;
        }
        startActivity(myIntent);
        finish();
    }
}

