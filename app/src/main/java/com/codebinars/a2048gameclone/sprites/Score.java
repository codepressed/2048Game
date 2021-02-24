package com.codebinars.a2048gameclone.sprites;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.codebinars.a2048gameclone.R;

public class Score implements Sprite {

    private static final String SCORE_PREF = "Score pref";

    private Resources resources;
    private int screenWidth, screenHeight, standardSize;
    private Bitmap bmpScore, bmpTopScore, bmpCopyright;
    private Bitmap bmpTopScoreBonus, bmp2048Bonus;
    private int score, topScore;
    private SharedPreferences prefs;
    private Paint paint;
    private boolean topScoreBonus = false;
    private boolean a2048Bonus = false;

    public Score(Resources resources, int screenWidth, int screenHeight, int standardSize, SharedPreferences prefs) {
        this.resources = resources;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = standardSize;
        this.prefs = prefs;

        topScore = prefs.getInt(SCORE_PREF, 0);
        int width = (int) resources.getDimension(R.dimen.score_label_width);
        int height = (int) resources.getDimension(R.dimen.score_label_height);
        int copyrightWidth = 1000;
        int copyrightHeight = 200;



        Bitmap sc = BitmapFactory.decodeResource(resources, R.drawable.score);
        bmpScore = Bitmap.createScaledBitmap(sc, width, height, false);

        Bitmap tsc = BitmapFactory.decodeResource(resources, R.drawable.topscore);
        bmpTopScore = Bitmap.createScaledBitmap(tsc, width, height, false);

        Bitmap copyright = BitmapFactory.decodeResource(resources, R.drawable.copyright);
        bmpCopyright = Bitmap.createScaledBitmap(copyright, copyrightWidth, copyrightHeight, false);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(resources.getDimension(R.dimen.score_text_size));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmpScore, screenWidth / 4 - bmpScore.getWidth() / 2, bmpScore.getHeight(), null);
        canvas.drawBitmap(bmpTopScore, 3 * screenWidth / 4 - bmpTopScore.getWidth() / 2, bmpTopScore.getHeight(), null);
        canvas.drawBitmap(bmpCopyright, 3 * screenWidth / 4 -  15 * bmpCopyright.getWidth() / 20, 10 * screenHeight / 12, null);

        int width1 = (int) paint.measureText(String.valueOf(score));
        int width2 = (int) paint.measureText(String.valueOf(topScore));
        canvas.drawText(String.valueOf(score), screenWidth / 4 - width1 / 2, bmpScore.getHeight() * 4, paint);
        canvas.drawText(String.valueOf(topScore), 3 * screenWidth / 4 - width2 / 2, bmpTopScore.getHeight() * 4, paint);
        canvas.drawText(("* USERNAME: Juan Gines *"),screenWidth / 12, (float) (screenHeight / 1.25), paint);
        //canvas.drawText(("GAME DEVELOPED by Dani Apesteguia"),screenWidth / 8, (float) (screenHeight / 1.2), paint);
        if (topScoreBonus) {
            canvas.drawBitmap(bmpTopScoreBonus, screenWidth / 2 - 2 * standardSize, screenHeight / 2 - 2 * standardSize - 2 * bmpTopScoreBonus.getHeight(), null);
        }
        if (a2048Bonus) {
            canvas.drawBitmap(bmp2048Bonus, screenWidth / 2 - 2 * standardSize, screenHeight / 2 - 2 * standardSize - 4 * bmp2048Bonus.getHeight(), null);
        }

    }

    @Override
    public void update() {

    }

    public void updateScore(int delta) {
        score += delta;
        checkTopScore();
    }

    /**
     * Check if score can be in Top 10 Scores
     */
    private void checkTopScore() {
        topScore = prefs.getInt(SCORE_PREF, 0);
        if (topScore < score) {
            prefs.edit().putInt(SCORE_PREF, score).apply();
            topScore = score;

            int width = (int) resources.getDimension(R.dimen.score_bonus_width);
            int height = (int) resources.getDimension(R.dimen.score_bonus_height);
            Bitmap tsb = BitmapFactory.decodeResource(resources, R.drawable.highscore);
            bmpTopScoreBonus = Bitmap.createScaledBitmap(tsb, width, height, false);
            topScoreBonus = true;
        }
    }

    public void reached2048() {
        a2048Bonus = true;
        int width = (int) resources.getDimension(R.dimen.score_bonus_width);
        int height = (int) resources.getDimension(R.dimen.score_bonus_height);
        Bitmap r2048bmp = BitmapFactory.decodeResource(resources, R.drawable.a2048);
        bmp2048Bonus = Bitmap.createScaledBitmap(r2048bmp, width, height, false);

    }
}