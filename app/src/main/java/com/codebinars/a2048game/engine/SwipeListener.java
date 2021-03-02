package com.codebinars.a2048game.engine;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeListener implements GestureDetector.OnGestureListener{
    private GestureDetector detector;
    private SwipeCallback callback;

    public SwipeListener(Context context, SwipeCallback callback){
        this.callback = callback;
        detector = new GestureDetector(context,this);
    }

    public void onTouchEvent(MotionEvent e){
        detector.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(Math.abs(velocityX) > Math.abs(velocityY)){
            //Horizontal Fling
            if(velocityX > 5){
                callback.onSwipe(SwipeCallback.Direction.RIGHT);

            } else if(velocityX < 5){
                callback.onSwipe(SwipeCallback.Direction.LEFT);
            }
            //Vertical Fling
        } else{
            if(velocityY > 5){
                callback.onSwipe(SwipeCallback.Direction.DOWN);
            } else if(velocityY < 5){
                callback.onSwipe(SwipeCallback.Direction.UP);
            }


        }
        return false;
    }
}
