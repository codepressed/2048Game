package com.codebinars.a2048game.engine.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.codebinars.a2048game.R;
import com.codebinars.a2048game.database.DBHelper;

public class Score implements Sprite {

    private Resources resources;
    private int screenWidth, screenHeight, standardSize;
    private Bitmap bmpScore, bmpTopScore, bmpUsertime;
    private Bitmap bmpTopScoreBonus, bmp2048Bonus;
    private int score, topScore, backupScore;
    private DBHelper dbHelper;
    private Paint paint;
    private boolean topScoreBonus = false;
    private boolean a2048Bonus = false;
    private String username;
    private long startTime;
    private long currentTimeMillis;
    private float currentTimeSeconds;

    public Score(Resources resources, int screenWidth, int screenHeight, int standardSize, DBHelper dbHelper, String username) {
        this.resources = resources;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = standardSize;
        this.dbHelper = dbHelper;
        this.username = username;
        this.startTime = System.currentTimeMillis();

        topScore = dbHelper.getTopScore();
        int width = (int) resources.getDimension(R.dimen.score_label_width);
        int height = (int) resources.getDimension(R.dimen.score_label_height);

        Bitmap sc = BitmapFactory.decodeResource(resources, R.drawable.scoresquare);
        bmpScore = Bitmap.createScaledBitmap(sc, width, height, false);

        Bitmap tsc = BitmapFactory.decodeResource(resources, R.drawable.topscoresquare);
        bmpTopScore = Bitmap.createScaledBitmap(tsc, width, height, false);

        Bitmap ut = BitmapFactory.decodeResource(resources, R.drawable.usertimetable);
        bmpUsertime = Bitmap.createScaledBitmap(ut, (int) resources.getDimension(R.dimen.user_and_time_width), (int) resources.getDimension(R.dimen.user_and_time_height), false);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(resources.getDimension(R.dimen.score_text_size));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmpScore, screenWidth / 4 - bmpScore.getWidth() / 2, bmpScore.getHeight()/9, null);
        canvas.drawBitmap(bmpTopScore, (float) (3.1 * screenWidth / 4 - bmpTopScore.getWidth() / 2), bmpTopScore.getHeight()/9, null);
        canvas.drawBitmap(bmpUsertime, screenWidth / 2 - bmpUsertime.getWidth()/2, (float) (screenHeight / 1.35), null);

        int width1 = (int) paint.measureText(String.valueOf(score));
        int width2 = (int) paint.measureText(String.valueOf(topScore));

        currentTimeMillis = System.currentTimeMillis() - startTime;
        currentTimeSeconds = currentTimeMillis / 1000F;

        canvas.drawText(String.valueOf(score), screenWidth / 4 - width1 / 2, bmpScore.getHeight(), paint);
        canvas.drawText(String.valueOf(topScore), 3 * screenWidth / 4 - width2 / 2, bmpTopScore.getHeight(), paint);
        canvas.drawText((username), (float) (screenWidth / 3.4), (float) (screenHeight / 1.29), paint);
        canvas.drawText(String.valueOf((currentTimeSeconds)),(float) (screenWidth / 2.5), (float) (screenHeight / 1.215), paint);
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
        topScore = dbHelper.getTopScore();
        if (topScore < score) {
            topScore = score;

            int width = (int) resources.getDimension(R.dimen.score_bonus_width);
            int height = (int) resources.getDimension(R.dimen.score_bonus_height);
            Bitmap tsb = BitmapFactory.decodeResource(resources, R.drawable.highscore);
            bmpTopScoreBonus = Bitmap.createScaledBitmap(tsb, width, height, false);
            topScoreBonus = true;
        }
    }

    /**
     * Once 2048 is Reached, show the win
     */
    public void reached2048() {
        a2048Bonus = true;
        int width = (int) resources.getDimension(R.dimen.score_bonus_width);
        int height = (int) resources.getDimension(R.dimen.score_bonus_height);
        Bitmap r2048bmp = BitmapFactory.decodeResource(resources, R.drawable.a2048);
        bmp2048Bonus = Bitmap.createScaledBitmap(r2048bmp, width, height, false);

    }

    /**
     * Save a BackUp of the score
     */
    public void setBackupScore(){
        this.backupScore = score;
    }

    /**
     * Undo score changes of last movement
     */
    public void restoreScore(){
        this.score = backupScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getCurrentTimeSeconds() {
        return currentTimeSeconds;
    }


}