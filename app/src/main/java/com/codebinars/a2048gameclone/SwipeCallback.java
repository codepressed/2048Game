package com.codebinars.a2048gameclone;

public interface SwipeCallback {
    void onSwipe(Direction direction);

    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
