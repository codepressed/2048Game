package com.codebinars.a2048gameclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.scoresView.ScoreListRecycler;
import static com.codebinars.a2048gameclone.scoresView.ScoreConstants.*;

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
        scoreId = extras.getInt(SCORE_ID);
        editScore.setText(extras.getString(SCORE_VALUE));
        editUsername.setText(extras.getString(SCORE_USERNAME));
        editDate.setText(extras.getString(SCORE_DATETIME));
        editDuration.setText(extras.getString(SCORE_DURATION));
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

