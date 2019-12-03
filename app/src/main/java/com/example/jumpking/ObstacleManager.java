package com.example.jumpking;

import android.graphics.Color;
import android.graphics.Rect;

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
            Obstacle ground = new Obstacle( new Rect(0,1500,1310,1600), Color.BLACK);
            obstacles1.add(ground);
            Obstacle leftblock = new Obstacle( new Rect(0,1300,300,1500), Color.BLACK);
            obstacles1.add(leftblock);
            Obstacle rightblock = new Obstacle( new Rect(800,1300,1300,1500), Color.BLACK);
            obstacles1.add(rightblock);
            Obstacle middleblock = new Obstacle( new Rect(400,900,700,1100), Color.BLACK);
            obstacles1.add(middleblock);
            Obstacle right2 = new Obstacle( new Rect(700,600,1300,700), Color.BLACK);
            obstacles1.add(right2);
            Obstacle left2 = new Obstacle( new Rect(0,300,400,400), Color.BLACK);
            obstacles1.add(left2);

            Obstacle ground2 = new Obstacle( new Rect(600,1700,1310,1800), Color.BLACK);
            obstacles2.add(ground2);



        }
        public ArrayList<Obstacle> getObstacles(int l){
            if(l == 1) {
                return obstacles1;
            }
            return obstacles2;
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
        for (Obstacle o : mainObstacles) {
            if(o.CollideRight(x,y)){
                return true;
            }
        }
        return false;
    }
    public boolean CollideLeft(int x, int y,int l){
        for (Obstacle o : mainObstacles) {
            if(o.CollideLeft(x,y)){
                return true;
            }
        }
        return false;
    }
    public int CollideTop(int x, int y,int l){

        for (Obstacle o : mainObstacles) {
            int res = o.CollideTop(x,y);
            if(res == 1){
                return 1;
            }
            else if(res == 2){
                return 2;
            }
        }
        return 0;
    }
}
