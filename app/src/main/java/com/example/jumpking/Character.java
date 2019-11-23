package com.example.jumpking;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.media.Image;

import static com.example.jumpking.MainThread.canvas;

public class Character {
    private Bitmap image;
    private Drawable background;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int x;
    private int y;


    public Character(Bitmap bmp, Drawable bg){
        image = bmp;


        background = bg;
        this.x= 100;
        this.y= 100;

    }

    public  void draw(Canvas canvas){
        background.draw(canvas);
        canvas.drawBitmap(image,x,y, null);

    }


    public void update(){
        x++;





    }
}
