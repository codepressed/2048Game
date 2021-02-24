package com.codebinars.a2048gameclone;

public interface GameManagerCallback {
    void gameOver();
    void updateScore(int delta);

    void reached2048();
}
