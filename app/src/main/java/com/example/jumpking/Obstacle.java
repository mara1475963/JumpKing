package com.example.jumpking;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Obstacle {
    private Rect rectangle;
    private int color;

    public Obstacle(Rect rectangle, int color){
            this.rectangle = rectangle;
            this.color = color;
    }

    public  void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }


    public int CollideBottom(int x, int y){
        if(y>1800){
            return 2;
        }
        if((rectangle.contains(x+20,y+90)) || (rectangle.contains(x+65,y+90))){
            return 1;
        }
        return 0;
    }
    public boolean CollideRight(int x, int y){
        if(x +85>Consts.screenWidth){
            return true;
        }
        if((rectangle.contains(x+90,y+20)) || (rectangle.contains(x+90,y+65))){
            return true;
        }

        return false;
    }
    public boolean CollideLeft(int x, int y){
        if(x<1){
            return true;
        }
        if((rectangle.contains(x-5,y+20)) || (rectangle.contains(x-5,y+65))){
            return true;
        }
        return false;
    }
    public int CollideTop(int x, int y){
        if(y<0){
            return 2;
        }


        if((rectangle.contains(x+20,y-15)) || (rectangle.contains(x+65,y-15))){
            return 1;
        }
        return 0;
    }
}
