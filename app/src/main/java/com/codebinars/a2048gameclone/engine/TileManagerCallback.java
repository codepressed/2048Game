package com.codebinars.a2048gameclone.engine;

import android.graphics.Bitmap;

import com.codebinars.a2048gameclone.engine.sprites.Tile;

public interface TileManagerCallback {
    Bitmap getBitmap(int count);
    void finishedMoving(Tile t);
    void updateScore(int delta);
    void reached2048();
}
