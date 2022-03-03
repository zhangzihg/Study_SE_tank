package com.zzh;

import java.awt.*;

public class Boom {
    private int x, y;
//    不需要这个标识，因为爆炸有开始就会有结束
//    private boolean happen = false;
    private TankFrame tankFrame;
    private int boomStar = 0;
    public Boom() {
    }

    public Boom(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
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
//        if(happen){
//            for(int i=0 ;i<ResourceMgr.Boom.length; i++){
//                g.drawImage(ResourceMgr.Boom[i],this.x,this.y,null);
//            }
//            this.happen = false;
//        }
        g.drawImage(ResourceMgr.Boom[boomStar++],x,y,null);

        //一个爆炸对象只会爆炸一次，如果爆炸播放完毕直接将其从爆炸数组删除
        if(boomStar >= ResourceMgr.Boom.length){
            tankFrame.getBooms().remove(this);
        }
    }
}
