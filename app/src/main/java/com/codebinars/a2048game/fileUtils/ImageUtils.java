package com.codebinars.a2048game.fileUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        if (bitmap == null){return null;}
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromBytes(byte[] data){
        if (data == null){return null;}
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public static byte[] getBytesFromImageView(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
       return getBytesFromBitmap(bitmap);
    }
}

