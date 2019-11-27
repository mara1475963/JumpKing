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
    private Bitmap left;
    private Bitmap right;
    private Drawable background;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int x;
    private int y;
    private int jumpHeight;
    private int lastPosition;
    private boolean top;
    private char lastDir;
    private boolean falling = false;

    public Character(Bitmap bmp, Drawable bg, Bitmap left, Bitmap right){
        image = bmp;
        this.left = left;
        this.right = right;

        background = bg;
        this.x= 500;
        this.y= 1450;
        this.jumpHeight = this.y;
        this.lastPosition = 1450;
        this.top = true;

    }

    public  void draw(Canvas canvas){
        background.draw(canvas);
        canvas.drawBitmap(image,x,y, null);
        canvas.drawBitmap(left, 100,1600, null);
        canvas.drawBitmap(right, 800,1600, null);

    }


    public void update(char dir){

        switch(dir){
            case 'S':

                if((this.y != jumpHeight) && !top){
                    if(lastDir == 'P'){
                        y-=4;
                        x+=4;
                    }
                    if(lastDir == 'L'){
                        y-=4;
                        x-=4;
                    }


                }
                else if((this.y != lastPosition)){
                    top = true;
                    falling = true;
                    if(lastDir == 'P'){
                        y+=4;
                        x+=4;
                    }
                    if(lastDir == 'L'){
                        y+=4;
                        x-=4;
                    }

                    jumpHeight = lastPosition;
                }
                else{
                    falling = false;
                }
                break;
            case 'P':
                if(!falling){
                    x+=2;
                    lastDir = 'P';
                }

                break;
            case 'L':
                if(!falling) {
                    x -= 2;
                    lastDir = 'L';
                }
                break;
            case 'N':
                if(!falling) {
                    jumpHeight -= 8;
                    top = false;
                }
                break;

        }





    }
}
