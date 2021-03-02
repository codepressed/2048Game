package com.codebinars.a2048game;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
public class GameActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
