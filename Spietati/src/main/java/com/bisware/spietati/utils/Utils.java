package com.bisware.spietati.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.URL;

public class Utils {

    public static Bitmap getBitmapFromURL(String src) {
        Bitmap bmp = null;
        String errore;
        try {
            URL url = new URL(src);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            errore = e.getMessage();
        }

        return bmp;
    }

    public static Drawable getDrawableFromURL(String src) {
        Drawable draw = null;
        String errore;
        try {
            URL url = new URL(src); //jpg
            draw = Drawable.createFromStream(url.openStream(), "src");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errore = e.getMessage();
        }

        return draw;
    }
}
