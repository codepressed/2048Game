package com.codebinars.a2048game.engine;

public interface GameTaskCallback {
    void gameOver();
    void updateScore(int delta);
    void reached2048();
}
