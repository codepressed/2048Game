package com.codebinars.a2048gameclone.engine;

public interface GameTaskCallback {
    void gameOver();
    void updateScore(int delta);
    void reached2048();
}
