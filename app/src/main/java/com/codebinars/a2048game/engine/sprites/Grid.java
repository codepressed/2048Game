package com.codebinars.a2048game.engine.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.codebinars.a2048game.R;

public class Grid implements Sprite{
    private Bitmap grid;
    private int screenWidth, screenHeight, standardSize;

    public Grid(Resources resources, int screenWidth, int screenHeight, int standardSize ){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = standardSize;

        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.grid);
        grid = Bitmap.createScaledBitmap(bmp,standardSize * 4,standardSize * 4,false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(grid,screenWidth/2-grid.getWidth()/2,screenHeight/2-grid.getHeight()/2,null);
    }

    @Override
    public void update() {

    }

}
