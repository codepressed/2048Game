package com.codebinars.a2048game.engine;

public interface SwipeCallback {
    void onSwipe(Direction direction);

    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
