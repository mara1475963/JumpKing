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

    public boolean playerLand(int x, int y){

        if(x<1 || x>Consts.screenWidth){
            return true;
        }
        return false;
    }

    public boolean CollideBottom(int x, int y){
            if((rectangle.contains(x+20,y+85)) || (rectangle.contains(x+80,y+90))){
                return true;
            }
        return false;
    }
    public boolean CollideRight(int x, int y){
        if(x +85>Consts.screenWidth){
            return true;
        }
        if((rectangle.contains(x+85,y)) || (rectangle.contains(x+85,y-80))){
            return true;
        }

        return false;
    }
    public boolean CollideLeft(int x, int y){
        if(x<1){
            return true;
        }
        if((rectangle.contains(x,y)) || (rectangle.contains(x,y-80))){
            return true;
        }

        return false;
    }
}
