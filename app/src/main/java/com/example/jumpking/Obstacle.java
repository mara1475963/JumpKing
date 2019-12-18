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
        paint.setStrokeWidth(2);
        canvas.drawRect(rectangle,paint);
    }


    public int CollideBottom(int x, int y){
        if(y>1800){
            return 2;
        }
        if((rectangle.contains(x +10,y+Consts.getCharY() +5)) || (rectangle.contains(x+Consts.getCharX()-10,y+Consts.getCharY() +5))){
            return 1;
        }
        return 0;
    }
    public boolean CollideRight(int x, int y){

        if(x + Consts.getCharX() > Consts.screenWidth){
            return true;
        }
        if((rectangle.contains(x+Consts.getCharX() + 5,y+10)) || (rectangle.contains(x+Consts.getCharX() + 5,y+Consts.getCharY()-10))){
            return true;
        }

        return false;
    }
    public boolean CollideLeft(int x, int y){
        if(x<10){
            return true;
        }
        if((rectangle.contains(x-10,y+10)) || (rectangle.contains(x-10,y+Consts.getCharX()-10))){
            return true;
        }
        return false;
    }
    public int CollideTop(int x, int y){
        if(y<0){
            return 2;
        }
        if((rectangle.contains(x+10,y-15)) || (rectangle.contains(x+Consts.getCharX()-10,y-15))){
            return 1;
        }
        return 0;
    }
}
