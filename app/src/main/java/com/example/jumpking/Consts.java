package com.example.jumpking;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.util.zip.CheckedOutputStream;

public class Consts {
    private static int charX;
    private static int charY;

    public Consts(int x, int y){
        charX = x;
        charY = y;
    }


    public static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static int getCharX() {
        return charX;
    }

    public static int getCharY() {
        return charY;
    }
}
