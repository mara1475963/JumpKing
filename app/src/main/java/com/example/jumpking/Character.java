package com.example.jumpking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;


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
    private Drawable background3;

    public int x;
    public int y;
    private int jumpHeight;
    private int jumpBar;
    private int maxJumpHeight;

    private int lastPosition;
    private boolean top;
    private char lastDir = 'P';
    private boolean falling;
    private ObstacleManager obstacleManager;
    private int LEVEL;
    private int posx;
    private int posy;
    private int jumpCount;
    private int fallCount;
    private boolean jumped;

    private int speed = 15;
    private int jumpspeed = 30;
    private boolean END = false;
    final MediaPlayer mp;
    final MediaPlayer mp2;

    public Character(Context c, Bitmap bmp, Drawable bg, Drawable bg2, Drawable bg3, Bitmap left, Bitmap right, Bitmap up, Bitmap left2, Bitmap right2, Bitmap up2, int level, int x, int y, int jumps, int falls){

        image = bmp;
        this.left = left;
        this.right = right;
        this.up = up;
        this.left2 = left2;
        this.right2 = right2;
        this.up2 = up2;

        background = bg;
        background2 = bg2;
        background3 = bg3;
        this.falling = false;
        this.LEVEL = level;
        this.x= x;
        this.y= y;
        this.jumpHeight = this.y;
        this.jumpHeight = 0;
        this.maxJumpHeight = 400;
        this.lastPosition = this.y;
        this.top = true;

        this.posx = 400;
        this.posy = 1600;
        this.jumpCount = jumps;
        this.fallCount = falls;
        this.jumped = false;
        this.obstacleManager = new ObstacleManager();
        obstacleManager.generetateView();

        mp = MediaPlayer.create(c,R.raw.fall);
        mp2 = MediaPlayer.create(c,R.raw.win);
    }

    public  void draw(Canvas canvas){

        if(LEVEL == 1) {
            background.draw(canvas);
        }
        else if(LEVEL == 2){
            background2.draw(canvas);
        }
        else if(LEVEL == 3){
            background3.draw(canvas);
            Paint paint = new Paint();

            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            posy -= 10;
            canvas.drawText("YOU WON", posx+50, posy, paint);
            canvas.drawText("TotalJumps: " + jumpCount, posx+30, posy+100, paint);
            canvas.drawText("TotalFalls: " + fallCount, posx+30, posy+200, paint);
            canvas.drawText("Author: Marek Bauer", posx-30, posy+300, paint);
            canvas.drawText("TAMZ2(2019)", posx+30, posy+400, paint);
        }
        canvas.drawBitmap(image,x,y, null);
        if(LEVEL != 3) {
            if (lastDir == 'L') {
                canvas.drawBitmap(left2, 0, 1660, null);
            } else {
                canvas.drawBitmap(left, 0, 1660, null);
            }
            if (lastDir == 'P') {
                canvas.drawBitmap(right2, 150, 1655, null);
            } else {
                canvas.drawBitmap(right, 150, 1655, null);
            }
            if (lastDir == 'U') {
                canvas.drawBitmap(up2, 300, 1660, null);
            } else {
                canvas.drawBitmap(up, 300, 1660, null);
            }

            Paint text = new Paint();
            text.setColor(Color.BLACK);
            text.setTextSize(30);
            canvas.drawText("Jumps: " + jumpCount, 50, 50, text);
            canvas.drawText("Falls: " + fallCount, 50, 100, text);
        }

        for (Obstacle o: obstacleManager.getObstacles(LEVEL)) {
            o.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        int size = (int)(505+(jumpBar*1.3));
        canvas.drawRect(new Rect(505,1660,size,1800),paint);
    }

    public void update(char dir){

        if(LEVEL == 3 && this.posy < -500){

            END = true;
            return;
        }
        if(LEVEL == 3 && this.y < -100){
            this.y = -100;
            return;
        }
        switch(dir){
            case 'S':
                jumpBar = 0;
                    if ((this.y > jumpHeight) && !top) {
                        int res = obstacleManager.CollideTop(this.x, this.y, LEVEL);
                        if(res ==1){
                            falling = true;
                            jumpHeight=y;
                            top = true;
                        }
                        else if(res == 2){
                            LEVEL =2;
                            this.y = 1800;
                            jumpHeight = 1800+jumpHeight;
                            lastPosition = 1800;
                            Log.d("height", String.valueOf(jumpHeight));
                        }
                        else if(res == 3){
                            mp2.start();
                            LEVEL = 3;
                            this.y = 1800;
                            jumpHeight = -200;
                        }
                        Log.d("levl", String.valueOf(LEVEL));
                        if(obstacleManager.CollideRight(this.x, this.y,LEVEL)){
                            lastDir = 'L';

                        }
                        if(obstacleManager.CollideLeft(this.x, this.y,LEVEL)){
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
                            if(jumped) {
                                jumpCount++;
                            }
                            jumped=false;
                            if(lastPosition < this.y){

                                mp.start();
                                fallCount++;
                            }
                            lastPosition = y;
                            return;
                        }
                        else if(res==2){
                                mp.start();
                                fallCount++;

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
                        jumpHeight = y;
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
                    jumped = true;
                    jumpHeight -= jumpspeed;
                    jumpBar += jumpspeed;
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
    public int getJumps(){
        return this.jumpCount;
    }
    public int getFalls(){
        return this.fallCount;
    }
    public boolean getEnd(){
        return this.END;
    }
}

