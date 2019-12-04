package com.example.jumpking;

import android.content.SharedPreferences;
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
import android.preference.PreferenceManager;
import android.util.Log;

import static com.example.jumpking.MainThread.canvas;

public class Character {
    private Bitmap image;
    private Bitmap left;
    private Bitmap right;
    private Bitmap up;
    private Bitmap left2;
    private Bitmap right2;
    private Bitmap up2;
    private Drawable background;
    private Drawable background2;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public int x;
    public int y;
    private int jumpHeight;
    private int jumpBar;
    private int maxJumpHeight;

    private int lastPosition;
    private boolean top;
    private char lastDir = 'P';
    private boolean falling = false;
    private ObstacleManager obstacleManager;
    private int LEVEL;

    private int speed = 15;

    public Character(Bitmap bmp, Drawable bg,Drawable bg2, Bitmap left, Bitmap right,Bitmap up, Bitmap left2, Bitmap right2,Bitmap up2, int level, int x, int y){

        image = bmp;
        this.left = left;
        this.right = right;
        this.up = up;
        this.left2 = left2;
        this.right2 = right2;
        this.up2 = up2;

        background = bg;
        background2 = bg2;
        this.falling = false;
        this.x= x;
        this.y= y;
        this.jumpHeight = this.y;
        this.jumpHeight = 0;
        this.maxJumpHeight = 400;
        this.lastPosition = this.y;
        this.top = true;
        this.LEVEL = level;

        this.obstacleManager = new ObstacleManager();
        obstacleManager.generetateView();
    }

    public  void draw(Canvas canvas){

        if(LEVEL == 1) {
            background.draw(canvas);
        }
        else if(LEVEL == 2){
            background2.draw(canvas);
        }
        canvas.drawBitmap(image,x,y, null);

        if(lastDir == 'L') {
            canvas.drawBitmap(left2, 0, 1660, null);
        }
        else{
            canvas.drawBitmap(left, 0, 1660, null);
        }
        if(lastDir == 'P') {
            canvas.drawBitmap(right2, 150, 1655, null);
        }
        else {
            canvas.drawBitmap(right, 150, 1655, null);
        }
        if(lastDir == 'U') {
            canvas.drawBitmap(up2, 300, 1660, null);
        }
        else {
            canvas.drawBitmap(up, 300, 1660, null);
        }

        for (Obstacle o: obstacleManager.getObstacles(LEVEL)) {
            o.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        int size = (int)(505+(jumpBar*1.4));
        canvas.drawRect(new Rect(505,1660,size,1800),paint);
    }

    public void update(char dir){
        switch(dir){
            case 'S':
                jumpBar = 0;
                    if ((this.y != jumpHeight) && !top) {
                        int res = obstacleManager.CollideTop(this.x, this.y, LEVEL);
                        if(res ==1){
                            falling = true;
                            jumpHeight=y;
                            top = true;
                        }
                        else if(res == 2){
                            LEVEL =2;
                            this.y = 1800;
                            jumpHeight = 1800-jumpHeight;
                        }
                        else if(obstacleManager.CollideRight(this.x, this.y,LEVEL)){
                            lastDir = 'L';
                        }
                        else if(obstacleManager.CollideLeft(this.x, this.y,LEVEL)){
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
                        if (lastDir == 'U') {
                            y -= speed;
                        }
                        falling = true;
                    } else if (falling) {
                        int res = obstacleManager.CollideBottom(this.x, this.y,LEVEL);
                        if(res==1){
                            falling = false;
                            return;
                        }
                        else if(res==2){
                            this.y = 0;
                            LEVEL = 1;
                            return;
                        }
                        if(obstacleManager.CollideRight(this.x, this.y,LEVEL)){
                            lastDir = 'L';
                        }
                        if(obstacleManager.CollideLeft(this.x, this.y,LEVEL)){
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
                        if (lastDir == 'U') {
                            y += speed;
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
                if(obstacleManager.CollideBottom(this.x,this.y,LEVEL) != 1){
                    y += speed;
                    x += speed;
                    falling = true;
                    return;
                }
                else {
                    falling = false;
                    jumpHeight = y;
                    top = true;
                    if (!obstacleManager.CollideRight(this.x, this.y,LEVEL)) {
                        x += speed;
                        lastDir = 'P';
                    }
                }
                break;
            case 'L':
                if(obstacleManager.CollideBottom(this.x,this.y,LEVEL) !=1){
                    y += speed;
                    x -= speed;
                    falling = true;
                    return;
                }
                else {
                    falling = false;
                    jumpHeight = y;
                    top = true;
                    if(!obstacleManager.CollideLeft(this.x, this.y,LEVEL )) {
                        x -= speed;
                        lastDir = 'L';
                    }
                }

                break;
            case 'N':
                if(jumpBar > maxJumpHeight){
                    return;
                }
                    jumpHeight -= speed;
                    jumpBar += speed;
                    top = false;
                    break;
            case 'U':
                lastDir = 'U';
                break;

        }

        Log.d("smer", String.valueOf(dir));
    }
    public boolean isFalling(){
        return falling;
    }
    public int getLevel(){
        return this.LEVEL;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
