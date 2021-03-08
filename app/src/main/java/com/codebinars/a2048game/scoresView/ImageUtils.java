package com.codebinars.a2048game.scoresView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImageUtils {
    public static Bitmap loadImage(String imageRoot){
        Bitmap bitmap;
        try {
            File file = new File (imageRoot);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (Exception e) {
            bitmap = null;
        }
        return bitmap;
    }
}
