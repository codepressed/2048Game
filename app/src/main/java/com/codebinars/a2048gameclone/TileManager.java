package com.codebinars.a2048gameclone;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.codebinars.a2048gameclone.sprites.Sprite;
import com.codebinars.a2048gameclone.sprites.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TileManager implements TileManagerCallback, Sprite {
    private Resources resources;
    private int standardSize, screenWidth, screenHeight;
    private ArrayList<Integer> drawables = new ArrayList<>();
    private HashMap<Integer, Bitmap> tileBitmaps = new HashMap<>();
    private Tile[][] matrix = new Tile[4][4];
    private boolean moving = false;
    private ArrayList<Tile> movingTiles;
    private boolean toSpawn = false;
    private boolean endGame = false;
    private GameManagerCallback callback;

    public TileManager(Resources resources, int standardSize, int screenWidth, int screenHeight, GameManagerCallback callback) {
        this.resources = resources;
        this.standardSize = standardSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callback = callback;
        initBitmaps();

        initGame();
    }

    private void initBitmaps(){
        drawables.add(R.drawable.one);
        drawables.add(R.drawable.two);
        drawables.add(R.drawable.three);
        drawables.add(R.drawable.four);
        drawables.add(R.drawable.five);
        drawables.add(R.drawable.six);
        drawables.add(R.drawable.seven);
        drawables.add(R.drawable.eight);
        drawables.add(R.drawable.nine);
        drawables.add(R.drawable.ten);
        drawables.add(R.drawable.eleven);
        drawables.add(R.drawable.twelve);
        drawables.add(R.drawable.thirteen);
        drawables.add(R.drawable.fourteen);
        drawables.add(R.drawable.fifteen);
        drawables.add(R.drawable.sixteen);

        for(int i=1; i<=16; i++){
            Bitmap bmp = BitmapFactory.decodeResource(resources, drawables.get(i-1));
            Bitmap tileBmp = Bitmap.createScaledBitmap(bmp, standardSize, standardSize,false);
            tileBitmaps.put(i,tileBmp);
        }
    }

    public void initGame() {
        matrix = new Tile[4][4];
        movingTiles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if (matrix[x][y] == null) {
                Tile tile = new Tile(standardSize, screenWidth, screenHeight, this, x, y);
                matrix[x][y] = tile;
            } else {
                i--;
            }
        }
    }

    @Override
    public Bitmap getBitmap(int count) {
        return tileBitmaps.get(count);
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != null) {
                    matrix[i][j].draw(canvas);
                }
            }
        }
        if (endGame) {
            callback.gameOver();
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != null) {
                    matrix[i][j].update();
                }
            }
        }
    }

    public void onSwipe(SwipeCallback.Direction direction) {
        if (!moving) {
            //moving = true;
            Tile[][] newMatrix = new Tile[4][4];
            switch (direction) {
                case UP:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = i - 1; k >= 0; k--) {
                                    if (newMatrix[k][j] == null) {
                                        newMatrix[k][j] = matrix[i][j];
                                        if (newMatrix[k + 1][j] == matrix[i][j]) {
                                            newMatrix[k + 1][j] = null;
                                        }
                                    } else if (newMatrix[k][j].getValue() == matrix[i][j].getValue() && !newMatrix[k][j].toIncrement()) {
                                        newMatrix[k][j] = matrix[i][j].increment();
                                        if (newMatrix[k + 1][j] == matrix[i][j]) {
                                            newMatrix[k + 1][j] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case DOWN:
                    for (int i = 3; i >= 0; i--) {
                        for (int j = 0; j < 4; j++) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = i + 1; k < 4; k++) {
                                    if (newMatrix[k][j] == null) {
                                        newMatrix[k][j] = matrix[i][j];
                                        if (newMatrix[k - 1][j] == matrix[i][j]) {
                                            newMatrix[k - 1][j] = null;
                                        }
                                    } else if (newMatrix[k][j].getValue() == matrix[i][j].getValue() && !newMatrix[k][j].toIncrement()) {
                                        newMatrix[k][j] = matrix[i][j].increment();
                                        if (newMatrix[k - 1][j] == matrix[i][j]) {
                                            newMatrix[k - 1][j] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 3; i >= 0; i--) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case LEFT:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = j - 1; k >= 0; k--) {
                                    if (newMatrix[i][k] == null) {
                                        newMatrix[i][k] = matrix[i][j];
                                        if (newMatrix[i][k + 1] == matrix[i][j]) {
                                            newMatrix[i][k + 1] = null;
                                        }
                                    } else if (newMatrix[i][k].getValue() == matrix[i][j].getValue() && !newMatrix[i][k].toIncrement()) {
                                        newMatrix[i][k] = matrix[i][j].increment();
                                        if (newMatrix[i][k + 1] == matrix[i][j]) {
                                            newMatrix[i][k + 1] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case RIGHT:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = j + 1; k < 4; k++) {
                                    if (newMatrix[i][k] == null) {
                                        newMatrix[i][k] = matrix[i][j];
                                        if (newMatrix[i][k - 1] == matrix[i][j]) {
                                            newMatrix[i][k - 1] = null;
                                        }
                                    } else if (newMatrix[i][k].getValue() == matrix[i][j].getValue() && !newMatrix[i][k].toIncrement()) {
                                        newMatrix[i][k] = matrix[i][j].increment();
                                        if (newMatrix[i][k - 1] == matrix[i][j]) {
                                            newMatrix[i][k - 1] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }

                    break;
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (newMatrix[i][j] != matrix[i][j]) {
                        toSpawn = true;
                        break;
                    }
                }
            }
            matrix = newMatrix;
        }
    }

    @Override
    public void finishedMoving(Tile t) {
        movingTiles.remove(t);
        if (movingTiles.isEmpty()) {
            moving = false;
            spawn();
            checkEndgame();
        }
    }

    /**
     * Method to check if game is over
     * First: Check if we can add more values to Tiles
     * Second: Check if there are value matches (Skip if First case is possible)
     */
    private void checkEndgame() {
        endGame = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == null) {
                    endGame = false;
                    break;
                }
            }
        }
        if (endGame) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((i > 0 && matrix[i - 1][j].getValue() == matrix[i][j].getValue()) ||
                            (i < 3 && matrix[i + 1][j].getValue() == matrix[i][j].getValue()) ||
                            (j > 0 && matrix[i][j - 1].getValue() == matrix[i][j].getValue()) ||
                            (j < 3 && matrix[i][j + 1].getValue() == matrix[i][j].getValue())) {
                        endGame = false;
                    }
                }
            }
        }
    }

    /**
     * Spawn a Number in random location.
     * If location has a value assigned in matrix, try again.
     */
    private void spawn() {
        if (toSpawn) {
            toSpawn = false;
            Tile t = null;
            while (t == null) {
                int x = new Random().nextInt(4);
                int y = new Random().nextInt(4);
                if (matrix[x][y] == null) {
                    t = new Tile(standardSize, screenWidth, screenHeight, this, x, y);
                    matrix[x][y] = t;
                }
            }
        }
    }

    @Override
    public void updateScore(int delta) {
        callback.updateScore(delta);
    }

    @Override
    public void reached2048() {
        callback.reached2048();
    }
}