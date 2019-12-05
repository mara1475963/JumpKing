package com.example.jumpking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import static android.graphics.BitmapFactory.decodeResource;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Character character;
    private char direction = 'S';
    SharedPreferences preset;
    SharedPreferences.Editor editor;

    public GameView(Context context){
        super(context);
        preset = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preset.edit();



        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int level;
        int x;
        int y;
        int jumps;
        int falls;

/*
        if(preset.getBoolean("com.example.jumpking.newGame",true)){
           level = 1;
           x = 500;
           y = 1415;
           jumps = 0;
           falls = 0;
        }
        else {
        */
           level = preset.getInt("com.example.jumpking.level", 1);
           x = preset.getInt("com.example.jumpking.x", 150);
           y = preset.getInt("com.example.jumpking.y", 200);
           jumps = preset.getInt("com.example.jumpking.jumps", 0);
           falls = preset.getInt("com.example.jumpking.falls", 0);
       // }

        Drawable d = getResources().getDrawable(R.drawable.background, null);
        d.setBounds(getLeft(),getTop(),getRight(),getBottom());
        Drawable d2 = getResources().getDrawable(R.drawable.background2, null);
        d2.setBounds(getLeft(),getTop(),getRight(),getBottom());
        Drawable d3 = getResources().getDrawable(R.drawable.background3, null);
        d3.setBounds(getLeft(),getTop(),getRight(),getBottom());
        Bitmap left = decodeResource(getResources(),R.drawable.left);
        Bitmap right = BitmapFactory.decodeResource(getResources(),R.drawable.right);
        Bitmap up = BitmapFactory.decodeResource(getResources(),R.drawable.up);
        Bitmap left2 = decodeResource(getResources(),R.drawable.left_pressed);
        Bitmap right2 = BitmapFactory.decodeResource(getResources(),R.drawable.right_pressed);
        Bitmap up2 = BitmapFactory.decodeResource(getResources(),R.drawable.up_pressed);
        character = new Character(BitmapFactory.decodeResource(getResources(),R.drawable.knight),d,d2,d3,left,right,up,left2,right2,up2,level,x,y, jumps,falls);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        character.update(direction);

        editor.putInt("com.example.jumpking.level",character.getLevel());
        editor.commit();
        editor.putInt("com.example.jumpking.x",character.getX());
        editor.commit();
        editor.putInt("com.example.jumpking.y",character.getY());
        editor.commit();
        editor.putInt("com.example.jumpking.jumps",character.getJumps());
        editor.commit();
        editor.putInt("com.example.jumpking.falls",character.getFalls());
        editor.commit();

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(character.isFalling()){
            e.setAction(MotionEvent.ACTION_UP);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("x", String.valueOf(e.getX()));
                Log.d("y", String.valueOf(e.getY()));

                if((e.getX() > 0 && e.getX() <150) && (e.getY() > 1660 && e.getY() <1820)){
                    direction = 'L';
                }
                else if((e.getX() > 150 && e.getX() <300) && (e.getY() > 1660 && e.getY() <1820)){
                    direction = 'P';
                }
                else if((e.getX() > 300 && e.getX() <450) && (e.getY() > 1660 && e.getY() <1820)){
                    direction = 'U';
                }
                else{
                    direction = 'N';
                }

                break;
            case MotionEvent.ACTION_UP:
                direction = 'S';
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {
            character.draw(canvas);
        }
    }

}
