package com.codebinars.a2048gameclone.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{

    private SurfaceHolder surfaceHolder;
    private GameTask gameTask;
    private int targetFPS = 60;
    private Canvas canvas;
    private Boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GameTask gameTask){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameTask = gameTask;
    }

    public void setRunning(Boolean isRunning){
        running = isRunning;
    }
    public void setSurfaceHolder(SurfaceHolder holder){
        surfaceHolder = holder;
    }

    @Override
    public void run() {
        long startTime, timeMillis,waitTime;
        long targetTime = 1000 / targetFPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    gameTask.update();
                    try {
                        gameTask.draw(canvas);
                    } catch (Exception e) {
                        setRunning(false);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try{
                if(waitTime > 0){
                    sleep(waitTime);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}