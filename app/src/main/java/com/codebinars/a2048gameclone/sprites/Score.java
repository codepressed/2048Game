package com.codebinars.a2048gameclone.sprites;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.codebinars.a2048gameclone.R;
import com.codebinars.a2048gameclone.database.DatabaseHelper;
import com.codebinars.a2048gameclone.database.ScoreModel;

import java.util.Calendar;
import java.util.Date;

public class Score implements Sprite {

    private Resources resources;
    private int screenWidth, screenHeight, standardSize;
    private Bitmap bmpScore, bmpTopScore, bmpCopyright;
    private Bitmap bmpTopScoreBonus, bmp2048Bonus;
    private int score, topScore;
    private DatabaseHelper databaseHelper;
    private Paint paint;
    private boolean topScoreBonus = false;
    private boolean a2048Bonus = false;
    private String username;
    private long startTime;
    private long currentTimeMillis;
    private float currentTimeSeconds;

    //Time
    private Date date;
    private Calendar calendar;

    public Score(Resources resources, int screenWidth, int screenHeight, int standardSize, DatabaseHelper databaseHelper, String username) {
        this.resources = resources;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = standardSize;
        this.databaseHelper = databaseHelper;
        this.username = username;
        this.startTime = System.currentTimeMillis();
        this.date = new Date();
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);

        topScore = databaseHelper.getTopScore();
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
        canvas.drawBitmap(bmpCopyright, 3 * screenWidth / 4 -  15 * bmpCopyright.getWidth() / 20, 20 * screenHeight / 23, null);

        int width1 = (int) paint.measureText(String.valueOf(score));
        int width2 = (int) paint.measureText(String.valueOf(topScore));
        currentTimeMillis = System.currentTimeMillis() - startTime;
        currentTimeSeconds = currentTimeMillis / 1000F;

        canvas.drawText(String.valueOf(score), screenWidth / 4 - width1 / 2, bmpScore.getHeight() * 4, paint);
        canvas.drawText(String.valueOf(topScore), 3 * screenWidth / 4 - width2 / 2, bmpTopScore.getHeight() * 4, paint);
        canvas.drawText(("USERNAME: " + username),screenWidth / 15, (float) (screenHeight / 1.25), paint);
        canvas.drawText(("Time consumed: " + currentTimeSeconds),screenWidth / 15, (float) (screenHeight / 1.2), paint);
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
        topScore = databaseHelper.getTopScore();
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
     * Method to save score details in a ScoreModel
     * It's used on GameManager to save it on DB
     */
    public void saveScore(){
        if (score > 1) {
            ScoreModel scoreModel = new ScoreModel();
            scoreModel.setScore(this.score);
            scoreModel.setUsername(this.username);
            // I'm adding +1 to Month because Calendar starts from 0.
            scoreModel.setDatetime(calendar.get(Calendar.DAY_OF_MONTH) + " - " + (calendar.get(Calendar.MONTH) + 1) + " - " + calendar.get(Calendar.YEAR));
            scoreModel.setDuration(currentTimeSeconds);
            databaseHelper.addScore(scoreModel);
        }
    }
}