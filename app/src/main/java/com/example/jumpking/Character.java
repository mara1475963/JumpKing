package com.example.jumpking;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;

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

    public int x;
    public int y;
    private int jumpHeight;
    private int lastPosition;
    private boolean top;
    private char lastDir = 'P';
    private boolean falling = false;
    private ObstacleManager obstacleManager;

    private int speed = 15;

    public Character(Bitmap bmp, Drawable bg, Bitmap left, Bitmap right){
        image = bmp;
        this.left = left;
        this.right = right;

        background = bg;
        this.falling = false;
        this.x= 500;
        this.y= 1400;
        this.jumpHeight = this.y;
        this.lastPosition = this.y;
        this.top = true;
        this.obstacleManager = new ObstacleManager();
        obstacleManager.generetateView();

    }

    public  void draw(Canvas canvas){
        background.draw(canvas);
        canvas.drawBitmap(image,x,y, null);
        canvas.drawBitmap(left, 100,1600, null);
        canvas.drawBitmap(right, 800,1600, null);

        for (Obstacle o: obstacleManager.getObstacles()) {
            o.draw(canvas);
        }
    }


    public void update(char dir){
        switch(dir){
            case 'S':

                if(obstacleManager.CollideBottom(this.x,this.y)){

                    falling = false;
                }
                    if ((this.y != jumpHeight) && !top) {
                        falling = true;
                        if(obstacleManager.CollideRight(this.x, this.y)){
                            lastDir = 'L';
                        }
                        if(obstacleManager.CollideLeft(this.x, this.y)){
                            lastDir = 'P';
                        }
                        if (lastDir == 'P') {
                            y -= speed;
                            x += speed;
                        }
                        if (lastDir == 'L') {
                            y -= speed;
                            x -= speed;
                        }
                    } else if (falling) {
                        if(obstacleManager.CollideBottom(this.x, this.y)){
                            falling = false;
                            return;
                        }
                        if(obstacleManager.CollideRight(this.x, this.y)){
                            lastDir = 'L';
                        }
                        if(obstacleManager.CollideLeft(this.x, this.y)){
                            lastDir = 'P';
                        }
                        top = true;

                        if (lastDir == 'P') {
                            y += speed;
                            x += speed;
                        }
                        if (lastDir == 'L') {
                            y += speed;
                            x -= speed;
                        }
                    }

                    else {
                        top = true;
                        lastPosition = y;
                        jumpHeight = lastPosition;
                        falling = false;
                    }
                break;
            case 'P':
                if(!obstacleManager.CollideBottom(this.x,this.y)){
                    y += speed;
                    x += speed;
                    falling = true;
                }
                else {
                    falling = false;
                    jumpHeight = y;
                    top = true;
                }

                    if (!falling && !obstacleManager.CollideRight(this.x, this.y)) {
                        x += speed;
                        lastDir = 'P';
                    }

                break;
            case 'L':
                if(!obstacleManager.CollideBottom(this.x,this.y)){
                    y += speed;
                    x -= speed;
                    falling = true;
                }
                else{
                    falling = false;
                }
                if(!falling && !obstacleManager.CollideLeft(this.x, this.y )) {
                    x -= speed;
                    lastDir = 'L';
                }
                break;
            case 'N':
                if(!falling) {
                    jumpHeight -= speed;
                    top = false;
                }
                break;

        }
        Log.d("smer", String.valueOf(dir));
    }
    public boolean isFalling(){
        return falling;
    }
}
