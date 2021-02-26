package com.codebinars.a2048gameclone.scoresView;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreListRecycler extends Activity {
    ArrayList<ScoreModel> listScores;
    RecyclerView recyclerViewScores;
    DatabaseHelper databaseHelper;
    ScoreListAdapter adapter;
    EditText usertop10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score_recycler);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        listScores = new ArrayList<>();
        recyclerViewScores = (RecyclerView) findViewById(R.id.recyclerScores);
        recyclerViewScores.setLayoutManager(new LinearLayoutManager(this));
        checkScoreList();
        adapter = new ScoreListAdapter(listScores);
        recyclerViewScores.setAdapter(adapter);
    }

    private void checkScoreList() {
        listScores = (ArrayList<ScoreModel>) databaseHelper.getAllScores();

    }

    /**
     * Buttons on RecyclerView layout
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.top10:
                listScores = (ArrayList<ScoreModel>) databaseHelper.getTop10();
                adapter.playersList = listScores;
                recyclerViewScores.setAdapter(adapter);
                break;
            case R.id.top10byuser:
                usertop10 = findViewById(R.id.textusertop10);
                String username = usertop10.getText().toString();
                listScores = (ArrayList<ScoreModel>) databaseHelper.getTop10ByUsername(username);
                adapter.playersList = listScores;
                recyclerViewScores.setAdapter(adapter);
                break;
            case R.id.deleteall:
                break;

        }
    }


}
