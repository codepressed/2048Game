package com.codebinars.a2048gameclone.scoresView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreListRecycler extends Activity implements AdapterView.OnItemSelectedListener{
    private ArrayList<ScoreModel> listScores;
    private RecyclerView recyclerViewScores;
    private DatabaseHelper databaseHelper;
    private ScoreListAdapter adapter;
    private EditText usertop10;
    private boolean sortedByUsername = false;
    private boolean sortedByScore = false;
    private boolean sortedByDuration = false;
    private boolean sortedByDate = false;

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

        //SCORE Filtering: Smaller than, equals to, bigger than
        Spinner scoreSpinner = findViewById(R.id.scoreSpinner);
        ArrayAdapter<CharSequence> scoreAdapter = ArrayAdapter.createFromResource(this, R.array.sortScoreValues, R.layout.support_simple_spinner_dropdown_item);
        scoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scoreSpinner.setAdapter(scoreAdapter);
        scoreSpinner.setOnItemSelectedListener(this);
    }

    /**
     * Method to load ScoreList
     */
    private void checkScoreList() {
        listScores = (ArrayList<ScoreModel>) databaseHelper.getAllScores();
    }

    /**
     * Method for sorting ArrayList of ScoreModel
     * @param option Option clicked
     */
    private void scoreSort(int option){
        switch (option){
            case 0: //Sort by USERNAME
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!sortedByUsername){
                        listScores.sort(Comparator.comparing(ScoreModel::getUsername));
                        sortedByUsername = true;}
                    else{
                        listScores.sort(Comparator.comparing(ScoreModel::getUsername).reversed());
                        sortedByUsername = false;
                    }
                }
                break;
            case 1: //Sort by SCORE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!sortedByScore){
                        listScores.sort(Comparator.comparing(ScoreModel::getScore));
                        sortedByScore = true;}
                    else{
                        listScores.sort(Comparator.comparing(ScoreModel::getScore).reversed());
                        sortedByScore = false;
                    }
                }
                break;
            case 2: //Sort by DURATION
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!sortedByDuration){
                        listScores.sort(Comparator.comparing(ScoreModel::getDuration));
                        sortedByDuration = true;}
                    else{
                        listScores.sort(Comparator.comparing(ScoreModel::getDuration).reversed());
                        sortedByDuration = false;
                    }
                }
                break;
            case 3: //Sort by GAMEDATE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!sortedByDate){
                        listScores.sort(Comparator.comparing(ScoreModel::getDatetime));
                        sortedByDate = true;}
                    else{
                        listScores.sort(Comparator.comparing(ScoreModel::getDatetime).reversed());
                        sortedByDate = false;
                    }
                }
                break;
        }
        adapter.playersList = listScores;
        recyclerViewScores.setAdapter(adapter);
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
            case R.id.resetFilters:
                break;
            //case R.id.deleteItem:
            //    System.out.println("Gmorning");
            case R.id.sortByUsername:
                scoreSort(0);
                break;
            case R.id.sortByScore:
                scoreSort(1);
                break;
            case R.id.sortByDuration:
                scoreSort(2);
                break;
            case R.id.sortByDatetime:
                scoreSort(3);
                break;


        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
