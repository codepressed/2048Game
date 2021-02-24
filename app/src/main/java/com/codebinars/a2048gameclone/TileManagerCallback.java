package com.codebinars.a2048gameclone;

import android.graphics.Bitmap;

import com.codebinars.a2048gameclone.sprites.Tile;

public interface TileManagerCallback {
    Bitmap getBitmap(int count);
    void finishedMoving(Tile t);
    void updateScore(int delta);

    void reached2048();
}
