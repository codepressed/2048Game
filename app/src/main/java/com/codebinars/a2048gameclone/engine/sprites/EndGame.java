package com.codebinars.a2048gameclone.engine.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.codebinars.a2048gameclone.R;

public class EndGame implements Sprite {

    private int screenWidth, screenHeight;
    private Bitmap bmp;

    public EndGame(Resources resources, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        int endgameWidth = (int) resources.getDimension(R.dimen.endgame_width);
        int endgameHeight = (int) resources.getDimension(R.dimen.endgame_height);

        Bitmap b = BitmapFactory.decodeResource(resources, R.drawable.gameover);
        bmp = Bitmap.createScaledBitmap(b, endgameWidth, endgameHeight, false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmp, screenWidth / 2 - bmp.getWidth() / 2, screenHeight / 2 - bmp.getHeight() / 2, null);
    }

    @Override
    public void update() {

    }
}