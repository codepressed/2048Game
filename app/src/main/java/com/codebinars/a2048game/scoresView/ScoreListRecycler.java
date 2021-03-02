package com.codebinars.a2048game.scoresView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebinars.a2048game.EditScoreActivity;
import com.codebinars.a2048game.R;
import com.codebinars.a2048game.database.DatabaseHelper;
import com.codebinars.a2048game.database.ScoreModel;
import static com.codebinars.a2048game.scoresView.ScoreConstants.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreListRecycler extends Activity implements AdapterView.OnItemSelectedListener{
    private final int SORT_BY_USERNAME=  0;
    private final int SORT_BY_SCORE =  1;
    private final int SORT_BY_DURATION =  2;
    private final int SORT_BY_DATETIME =  3;
    private final String TWEETBASE = "https://twitter.com/intent/tweet?text=In Rionacko's 2048 game, I have achieved the score of ";

    private ArrayList<ScoreModel> listScores;
    private RecyclerView recyclerViewScores;
    private DatabaseHelper databaseHelper;
    private ScoreListAdapter adapter;
    private EditText usertop10, filterScoreNumber;
    private Spinner spinnerScore;
    private Intent myIntent;
    private boolean sortedByUsername = false;
    private boolean sortedByScore = false;
    private boolean sortedByDuration = false;
    private boolean sortedByDate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score_recycler);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        listScores = new ArrayList<>();
        recyclerViewScores = findViewById(R.id.recyclerScores);
        recyclerViewScores.setLayoutManager(new LinearLayoutManager(this));
        checkScoreList();
        adapter = new ScoreListAdapter(listScores);
        recyclerViewScores.setAdapter(adapter);

        adapter.setOnItemclickListener(new OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onEditClick(int position) {
                myIntent = new Intent(ScoreListRecycler.this, EditScoreActivity.class);
                myIntent.putExtra(SCORE_ID, listScores.get(position).getId());
                myIntent.putExtra(SCORE_VALUE, listScores.get(position).getScore().toString());
                myIntent.putExtra(SCORE_USERNAME, listScores.get(position).getUsername());
                myIntent.putExtra(SCORE_DATETIME, listScores.get(position).getDatetime());
                myIntent.putExtra(SCORE_DURATION, listScores.get(position).getDuration().toString());
                startActivity(myIntent);
                finish();
            }

            @Override
            public void onTweetClick(int position) {
                int tweetScore = listScores.get(position).getScore();
                String tweetUrl = TWEETBASE+tweetScore;
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });



        //SCORE Filtering: Smaller than, equals to, bigger than
        Spinner scoreSpinner = findViewById(R.id.scoreSpinner);
        ArrayAdapter<CharSequence> scoreAdapter = ArrayAdapter.createFromResource(this, R.array.sortScoreValues, R.layout.support_simple_spinner_dropdown_item);
        scoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scoreSpinner.setAdapter(scoreAdapter);
        scoreSpinner.setOnItemSelectedListener(this);
    }

    private void removeItem(int position) {
        databaseHelper.deleteByID(listScores.get(position).getId());
        listScores.remove(position);
        adapter.notifyItemRemoved(position);

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
            case 0:
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
            case 1:
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
            case 2:
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
            case 3:
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
     * Filter ScoreArrayList by Score nº
     */
    public void filterByScore(String option, int filterScore){
         if (option.equals("Bigger than")){
            for (int i = 0; i < listScores.size(); i++) {
                if (listScores.get(i).getScore() <= filterScore){
                    listScores.remove(i--);
                }
            }
        }
        else if (option.equals("Smaller than")){
            for (int i = 0; i < listScores.size(); i++) {
                if (listScores.get(i).getScore() >= filterScore){
                    listScores.remove(i--);
                }
            }
        }
        else{
            for (int i = 0; i < listScores.size(); i++) {
                if (listScores.get(i).getScore() != filterScore){
                    listScores.remove(i--);
                }
            }
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
                checkScoreList();
                adapter.playersList = listScores;
                recyclerViewScores.setAdapter(adapter);
                break;
            case R.id.filterButton:
                filterScoreNumber = findViewById(R.id.scoreFilter);
                int scoreForFilter = Integer.parseInt(filterScoreNumber.getText().toString());
                spinnerScore = findViewById(R.id.scoreSpinner);
                String scoreOperator = spinnerScore.getSelectedItem().toString();
                filterByScore(scoreOperator, scoreForFilter);
            case R.id.sortByUsername:
                scoreSort(SORT_BY_USERNAME);
                break;
            case R.id.sortByScore:
                scoreSort(SORT_BY_SCORE);
                break;
            case R.id.sortByDuration:
                scoreSort(SORT_BY_DURATION);
                break;
            case R.id.sortByDatetime:
                scoreSort(SORT_BY_DATETIME);
                break;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
