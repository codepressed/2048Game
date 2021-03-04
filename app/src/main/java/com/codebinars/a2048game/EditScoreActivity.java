package com.codebinars.a2048game;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.codebinars.a2048game.database.DatabaseHelper;
import com.codebinars.a2048game.fileUtils.ImageUtils;
import com.codebinars.a2048game.scoresView.ScoreListRecycler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codebinars.a2048game.scoresView.ScoreConstants.*;

public class EditScoreActivity extends Activity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private DatabaseHelper databaseHelper;
    private EditText editUsername, editScore, editDuration, editCountry;
    private TextView editDate;
    private ImageView avatarView;
    private int scoreId;
    private Bitmap avatarImage;
    private Calendar calendar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_score);

        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        };

        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        editScore = findViewById(R.id.editScoreCamp);
        editUsername = findViewById(R.id.editUsernameCamp);
        editDate = findViewById(R.id.editDateCamp);
        editDuration = findViewById(R.id.editDurationCamp);
        editCountry = findViewById(R.id.editCountryCamp);
        avatarView = findViewById(R.id.avatarDisplay);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        scoreId = extras.getInt(SCORE_ID);
        editScore.setText(extras.getString(SCORE_VALUE));
        editDuration.setText(extras.getString(SCORE_DURATION));
        editUsername.setText(extras.getString(USER_NAME));
        editCountry.setText(extras.getString(USER_COUNTRY));
        editDate.setText(extras.getString(SCORE_DATETIME));
        editDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditScoreActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void onClick(View view) {
        Intent myIntent = null;
        switch (view.getId()) {
            case R.id.editAvatarButton:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) { //Permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else{ //Permission already granted
                        pickImageFromGallery();
                    }
                }
                else{ //System OS is less then marshmallow
                    pickImageFromGallery();
                }
                break;
            case R.id.updateLocation:
                editCountry.setText(getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry());
                break;
            case R.id.savechanges:
                try {
                    databaseHelper.updateScore(
                            scoreId,
                            editUsername.getText().toString(),
                            Integer.valueOf(editScore.getText().toString()),
                            editDate.getText().toString(),
                            Float.parseFloat(editDuration.getText().toString()));
                    databaseHelper.updateUser(editUsername.getText().toString().toLowerCase(), ImageUtils.getBytesFromBitmap(avatarImage), editCountry.getText().toString());
                    myIntent = new Intent(EditScoreActivity.this, ScoreListRecycler.class);
                    startActivity(myIntent);
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Wrong Datatype. Fix it if you want to save it",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.undochanges:
                myIntent = new Intent(EditScoreActivity.this, ScoreListRecycler.class);
                startActivity(myIntent);
                finish();
                break;
        }

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void updateDate(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        editDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission was granted
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this, "Permission denied ... !", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                avatarImage = BitmapFactory.decodeStream(inputStream);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            avatarView.setImageBitmap(avatarImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

