package com.codebinars.a2048gameclone;

import android.app.Activity;
import android.os.Bundle;

import com.codebinars.a2048gameclone.database.DatabaseHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}