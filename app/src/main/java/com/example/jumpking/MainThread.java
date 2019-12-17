package com.example.jumpking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public static final int MaxFPS = 30;
    private double averageFPS;



    public  MainThread(SurfaceHolder surfaceHolder, GameView gameView){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }
    @Override
    public void run() {
        long startTime;
        long timeMilisec = 1000/MaxFPS;
        long waitTime;
        int frameCount = 0;
        long totalTime =0;
        long targetTime = 1000/MaxFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {}
             finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMilisec = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMilisec;

            try{
        if(waitTime >0){
            this.sleep(waitTime);
        }
            }catch(Exception e){
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MaxFPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("FPS: " + averageFPS);
            }

        }
    }

    public void doLose(Context mContext) {
        synchronized (surfaceHolder) {
            ((Activity) mContext).finish();
        }
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }


}

