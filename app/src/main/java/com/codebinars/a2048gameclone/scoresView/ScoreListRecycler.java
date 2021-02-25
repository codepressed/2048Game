package com.codebinars.a2048gameclone.scoresView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;

import java.util.ArrayList;

public class ScoreListRecycler extends AppCompatActivity {
    ArrayList<ScoreModel> listScores;
    RecyclerView recyclerViewScores;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score_recycler);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        listScores = new ArrayList<>();
        recyclerViewScores = (RecyclerView) findViewById(R.id.recyclerScores);
        recyclerViewScores.setLayoutManager(new LinearLayoutManager(this));
        checkScoreList();
        ScoreListAdapter adapter = new ScoreListAdapter(listScores);
        recyclerViewScores.setAdapter(adapter);


    }

    private void checkScoreList() {
        listScores = (ArrayList<ScoreModel>) databaseHelper.getTop10();

    }
}
