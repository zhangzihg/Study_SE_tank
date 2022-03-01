package com.zzh;

import java.awt.*;

public class Boom {
    private int x, y;
    private boolean happen = false;

    public Boom() {
    }

    public Boom(int x, int y, boolean happen) {
        this.x = 100;
        this.y = 100;
        this.happen = true;
    }

    public boolean isHappen() {
        return happen;
    }

    public void setHappen(boolean happen) {
        this.happen = happen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        System.out.println("boom!!");
        int count =ResourceMgr.Boom.length;
        if(happen){
            for(int i=0 ;i<ResourceMgr.Boom.length; i++){
                g.drawImage(ResourceMgr.Boom[i],this.x,this.y,null);
            }
            this.happen = false;
        }
    }
}
