package com.example.jumpking;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles1;


        public  ObstacleManager(){
            obstacles1 = new ArrayList<>();
        }
        public void generetateView(){
            Obstacle ground = new Obstacle( new Rect(0,1500,Consts.screenWidth,1600), Color.BLACK);
            obstacles1.add(ground);
            Obstacle leftblock = new Obstacle( new Rect(0,1300,300,1500), Color.BLACK);
            obstacles1.add(leftblock);
            Obstacle righttblock = new Obstacle( new Rect(Consts.screenWidth-300,1300,Consts.screenWidth,1500), Color.BLACK);
            obstacles1.add(righttblock);
        }
        public ArrayList<Obstacle> getObstacles(){
            return obstacles1;
        }
        public boolean Collide(int x, int y){
            for (Obstacle o : obstacles1) {
                if(o.playerLand(x,y)){
                    return true;
                }
            }
            return false;
        }
    public boolean CollideBottom(int x, int y){
        for (Obstacle o : obstacles1) {
            if(o.CollideBottom(x,y)){
                return true;
            }
        }
        return false;
    }

    public boolean CollideRight(int x, int y){
        for (Obstacle o : obstacles1) {
            if(o.CollideRight(x,y)){
                return true;
            }
        }
        return false;
    }
    public boolean CollideLeft(int x, int y){
        for (Obstacle o : obstacles1) {
            if(o.CollideLeft(x,y)){
                return true;
            }
        }
        return false;
    }
}
