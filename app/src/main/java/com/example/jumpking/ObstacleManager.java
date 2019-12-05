package com.example.jumpking;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.logging.Level;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles1;
    private ArrayList<Obstacle> mainObstacles;
    private ArrayList<Obstacle> obstacles2;


        public  ObstacleManager(){
            obstacles1 = new ArrayList<>();
            obstacles2 = new ArrayList<>();
            mainObstacles = new ArrayList<>();
        }
        public void generetateView(){
            //LEVEL 1
            Obstacle ground = new Obstacle( new Rect(0,1500,1310,1600), Color.GRAY);
            obstacles1.add(ground);
            Obstacle leftblock = new Obstacle( new Rect(0,1300,300,1500), Color.GRAY);
            obstacles1.add(leftblock);
            Obstacle rightblock = new Obstacle( new Rect(800,1300,1300,1500), Color.GRAY);
            obstacles1.add(rightblock);
            Obstacle middleblock = new Obstacle( new Rect(400,900,700,1100), Color.GRAY);
            obstacles1.add(middleblock);
            Obstacle right2 = new Obstacle( new Rect(900,600,1300,700), Color.GRAY);
            obstacles1.add(right2);
            Obstacle left2 = new Obstacle( new Rect(0,300,400,400), Color.GRAY);
            obstacles1.add(left2);
            //LEVEL 2
            Obstacle ground2 = new Obstacle( new Rect(600,1700,1310,1800), Color.BLUE);
            obstacles2.add(ground2);
            Obstacle left3= new Obstacle( new Rect(200,1450,300,1500), Color.BLUE);
            obstacles2.add(left3);
            Obstacle right3= new Obstacle( new Rect(700,1300,800,1350), Color.BLUE);
            obstacles2.add(right3);
            Obstacle left4= new Obstacle( new Rect(0,1000,200,1050), Color.BLUE);
            obstacles2.add(left4);
            Obstacle left5= new Obstacle( new Rect(300,680,500,800), Color.BLUE);
            obstacles2.add(left5);
            Obstacle right4= new Obstacle( new Rect(700,500,1100,600), Color.BLUE);
            obstacles2.add(right4);
            Obstacle left6= new Obstacle( new Rect(200,200,500,250), Color.BLUE);
            obstacles2.add(left6);
        }
        public ArrayList<Obstacle> getObstacles(int l){
            if(l == 1) {
                return obstacles1;
            }
            else if(l==2){
                return obstacles2;
            }
            return new ArrayList<Obstacle>();
        }

    public int CollideBottom(int x, int y, int l){
        if(l == 1){
            mainObstacles = obstacles1;
        }
        else{
            mainObstacles = obstacles2;
        }

        for (Obstacle o : mainObstacles) {
            int res = o.CollideBottom(x,y);
            if(res == 1){
                return 1;
            }
            else if(res == 2){
                return 2;
            }
        }
        return 0;
    }

    public boolean CollideRight(int x, int y,int l){
        if(l==3){
            if( new Obstacle(new Rect(0,0,0,0),Color.BLACK).CollideRight(x,y)){
                return true;
            }
        }
        for (Obstacle o : mainObstacles) {
            if(o.CollideRight(x,y)){
                return true;
            }
        }
        return false;
    }
    public boolean CollideLeft(int x, int y,int l){
        if(l==3){
            if( new Obstacle(new Rect(0,0,0,0),Color.BLACK).CollideLeft(x,y)){
                return true;
            }
        }
        for (Obstacle o : mainObstacles) {
            if(o.CollideLeft(x,y)){
                return true;
            }
        }
        return false;
    }
    public int CollideTop(int x, int y,int l){
        if(l == 3){
            return 0;
        }

        for (Obstacle o : mainObstacles) {
            int res = o.CollideTop(x,y);
            if(res == 1){
                return 1;
            }
            else if(res == 2){
                mainObstacles = obstacles2;
                if(l==2){
                    mainObstacles = new ArrayList<Obstacle>();
                    return 3;
                }
                return 2;
            }
        }
        return 0;
    }
}
