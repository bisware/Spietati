package com.bisware.spietati.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
