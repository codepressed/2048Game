package com.codebinars.a2048gameclone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;


import androidx.annotation.NonNull;

import com.codebinars.a2048gameclone.sprites.EndGame;
import com.codebinars.a2048gameclone.sprites.Grid;
import com.codebinars.a2048gameclone.sprites.Score;

public class GameManager extends SurfaceView implements SurfaceHolder.Callback, SwipeCallback, GameManagerCallback {

    private static final String APP_NAME = "2048 plus";

    private MainThread thread;
    private Grid grid;
    private int scWidth, scHeight, standardSize;
    private TileManager tileManager;
    private boolean endGame = false;
    private EndGame endgameSprite;
    private Score score;
    private Bitmap restartButton;
    private int restartButtonX, restartButtonY, restartButtonSize;

    private SwipeListener swipe;

    public GameManager(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        setLongClickable(true);
        getHolder().addCallback(this);
        swipe = new SwipeListener(getContext(), this);

        scWidth = getScreenWidth((Activity)context);
        scHeight = getScreenHeight((Activity)context);

        standardSize = (int) (scWidth*0.88)/4;

        grid = new Grid(getResources(),scWidth,scHeight,standardSize);
        tileManager = new TileManager(getResources(), standardSize, scWidth, scHeight, this);
        endgameSprite = new EndGame(getResources(), scWidth, scHeight);
        score = new Score(getResources(), scWidth, scHeight, standardSize, getContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE));

        restartButtonSize = (int) getResources().getDimension(R.dimen.restart_button_size);
        Bitmap bmpRestart = BitmapFactory.decodeResource(getResources(), R.drawable.restart);
        restartButton = Bitmap.createScaledBitmap(bmpRestart, restartButtonSize, restartButtonSize, false);
        restartButtonX = scWidth / 2 + 2 * standardSize - restartButtonSize;
        restartButtonY = scHeight / 2 - 2 * standardSize - 3 * restartButtonSize / 2;

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
        score = new Score(getResources(), scWidth, scHeight, standardSize, getContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE));
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
        canvas.drawRGB(255,229,191);
        grid.draw(canvas);
        tileManager.draw(canvas);
        score.draw(canvas);
        canvas.drawBitmap(restartButton, restartButtonX, restartButtonY, null);
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
            if (event.getAction() == MotionEvent.ACTION_DOWN && eventX > restartButtonX && eventX < restartButtonX + restartButtonSize &&
                    eventY > restartButtonY && eventY < restartButtonY + restartButtonSize) {
                initGame();
            }
            swipe.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSwipe(Direction direction) {
        tileManager.onSwipe(direction);
    }

    @Override
    public void gameOver() {
        endGame = true;
    }

    @Override
    public void updateScore(int delta) {
        score.updateScore(delta);
    }

    @Override
    public void reached2048() {
        score.reached2048();
    }
}