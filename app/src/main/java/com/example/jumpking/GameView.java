package com.example.jumpking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        Drawable d = getResources().getDrawable(R.drawable.background, null);
        d.setBounds(getLeft(),getTop(),getRight(),getBottom());
        Drawable d2 = getResources().getDrawable(R.drawable.background2, null);
        d2.setBounds(getLeft(),getTop(),getRight(),getBottom());
        Bitmap left = decodeResource(getResources(),R.drawable.left);
        Bitmap right = BitmapFactory.decodeResource(getResources(),R.drawable.right);
        character = new Character(BitmapFactory.decodeResource(getResources(),R.drawable.knight),d,d2,left,right);

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
        editor.putInt("com.example.jumpking.level",1);
        editor.commit();
        editor.putInt("com.example.jumpking.x",1);
        editor.commit();
        editor.putInt("com.example.jumpking.y",1);
        editor.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(character.isFalling()){
            direction = 'S';
            return true;
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("x", String.valueOf(e.getX()));
                Log.d("y", String.valueOf(e.getY()));

                if((e.getX() > 100 && e.getX() <300) && (e.getY() > 1600 && e.getY() <1700)){
                    direction = 'L';
                }
                else if((e.getX() > 800 && e.getX() <1000) && (e.getY() > 1600 && e.getY() <1700)){
                    direction = 'P';
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
