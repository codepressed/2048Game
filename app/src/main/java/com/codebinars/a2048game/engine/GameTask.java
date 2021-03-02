package com.codebinars.a2048game.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowInsets;
import android.view.WindowMetrics;


import androidx.annotation.NonNull;

import com.codebinars.a2048game.R;
import com.codebinars.a2048game.database.DatabaseHelper;
import com.codebinars.a2048game.database.ScoreModel;
import com.codebinars.a2048game.engine.sprites.EndGame;
import com.codebinars.a2048game.engine.sprites.Grid;
import com.codebinars.a2048game.engine.sprites.Score;

import java.util.Calendar;

import static com.codebinars.a2048game.scoresView.ScoreConstants.SCORE_USERNAME;

public class GameTask extends SurfaceView implements SurfaceHolder.Callback, SwipeCallback, GameTaskCallback {

    private MainThread thread;
    private Grid grid;
    private int scWidth, scHeight, standardSize;
    private TileManager tileManager;
    private boolean endGame = false;
    private EndGame endgameSprite;
    private Score score;
    private Bitmap restartButton, undomovementButton, bmpCopyright;
    private int restartButtonX, restartButtonY, undoMovementX, undoMovementY;
    private DatabaseHelper databaseHelper;
    private Boolean scoreSaved = false;
    private String username;
    private int buttonHeight, buttonWidth;
    private SwipeListener swipe;

    public GameTask(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        setLongClickable(true);
        getHolder().addCallback(this);

        //Get username
        Activity activity = (Activity) context;
        Bundle extras = activity.getIntent().getExtras();
        username = extras.getString(SCORE_USERNAME);

        swipe = new SwipeListener(getContext(), this);
        scWidth = getScreenWidth((Activity)context);
        scHeight = getScreenHeight((Activity)context);
        standardSize = (int) (scWidth*0.88)/4;
        databaseHelper = DatabaseHelper.getInstance((Activity) context);

        grid = new Grid(getResources(),scWidth,scHeight,standardSize);
        tileManager = new TileManager(getResources(), standardSize, scWidth, scHeight, this);
        endgameSprite = new EndGame(getResources(), scWidth, scHeight);
        score = new Score(getResources(), scWidth, scHeight, standardSize, databaseHelper, username);

        buttonWidth = (int) getResources().getDimension(R.dimen.button_width);
        buttonHeight = (int) getResources().getDimension(R.dimen.button_height);
        Bitmap bmpRestart = BitmapFactory.decodeResource(getResources(), R.drawable.restartgame);
        restartButton = Bitmap.createScaledBitmap(bmpRestart, buttonWidth, buttonHeight, false);
        restartButtonX = scWidth / 2 + 2 * standardSize - buttonWidth;
        restartButtonY = scHeight / 2 - 2 * standardSize - 3 * buttonHeight / 2;


        Bitmap bmpUndoMovement = BitmapFactory.decodeResource(getResources(), R.drawable.undomovement);
        undomovementButton = Bitmap.createScaledBitmap(bmpUndoMovement, buttonWidth, buttonHeight, false);
        undoMovementX = scWidth / 2 + 2 * standardSize - buttonWidth;
        undoMovementY = scHeight / 2 - 2 * standardSize - 6 * buttonHeight / 2;

        int copyrightWidth = 1000;
        int copyrightHeight = 200;
        Bitmap copyright = BitmapFactory.decodeResource(getResources(), R.drawable.copyright);
        bmpCopyright = Bitmap.createScaledBitmap(copyright, copyrightWidth, copyrightHeight, false);

    }
    /**
     * These methods are to check Android Version and, depending of the version,
     * Fix size with one resolution or the other.
     * API Level 13 deprecated getDefaultDisplay and added new functions
     * But we want to keep the methods in case Android version is lower
     */
    public static int getScreenWidth(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    public static int getScreenHeight(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().height() - insets.top - insets.bottom;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }



    public void initGame() {
        endGame = false;
        tileManager.initGame();
        score = new Score(getResources(), scWidth, scHeight, standardSize, databaseHelper, username);
        scoreSaved = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(holder,this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceHolder(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if (!endGame) {
            tileManager.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getResources().getColor(R.color.bgColor));
        grid.draw(canvas);
        tileManager.draw(canvas);
        score.draw(canvas);
        canvas.drawBitmap(restartButton, restartButtonX, restartButtonY, null);
        canvas.drawBitmap(undomovementButton, undoMovementX, undoMovementY, null);
        canvas.drawBitmap(bmpCopyright, 3 * scWidth / 4 -  15 * bmpCopyright.getWidth() / 20, 20 * scHeight / 23, null);
        if (endGame) {
            endgameSprite.draw(canvas);
            endGame = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (endGame) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initGame();
            }
        } else {
            float eventX = event.getAxisValue(MotionEvent.AXIS_X);
            float eventY = event.getAxisValue(MotionEvent.AXIS_Y);
            //Check if RESTARD GAME was pressed
            if (event.getAction() == MotionEvent.ACTION_DOWN && eventX > restartButtonX && eventX < restartButtonX + buttonWidth &&
                    eventY > restartButtonY && eventY < restartButtonY + buttonHeight) {
                initGame();
            }
            //Check if UNDO MOVEMENT was pressed
            if(event.getAction() == MotionEvent.ACTION_DOWN && eventX > undoMovementX && eventX < undoMovementX + buttonWidth &&
                    eventY > undoMovementY && eventY < undoMovementY + buttonHeight){
                restoreBackup();
            }
            swipe.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void restoreBackup() {
        score.restoreScore();
        tileManager.restoreMatrix();
    }

    @Override
    public void onSwipe(Direction direction) {
        score.setBackupScore();
        tileManager.onSwipe(direction);
    }

    @Override
    public void gameOver() {
        endGame = true;
        if (!scoreSaved){
            saveScore();
            scoreSaved = true;
        }
    }

    @Override
    public void updateScore(int delta) {
        score.updateScore(delta);
    }

    @Override
    public void reached2048() {
        score.reached2048();
    }

    public void saveScore(){
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setScore(score.getScore());
        scoreModel.setUsername(this.username);
        scoreModel.setDatetime(score.getCalendar().get(Calendar.DAY_OF_MONTH) + " - " + (score.getCalendar().get(Calendar.MONTH) + 1) + " - " + score.getCalendar().get(Calendar.YEAR));
        scoreModel.setDuration(score.getCurrentTimeSeconds());
        databaseHelper.addScore(scoreModel);
        }

    public void closeDb(){
        databaseHelper.close();
    }
}