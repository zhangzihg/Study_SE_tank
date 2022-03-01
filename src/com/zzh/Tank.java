package com.zzh;

import java.awt.*;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private final static int SPEED = 8;
    private boolean moving = false;
    private TankFrame tankFrame = null;
    private boolean alive = true;
    private Group group;

    public static  int T_WEIGHT ;
    public static  int T_HEIGHT ;
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;

    }


    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Tank() {
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x=x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        if(!alive) return;
        if(!moving) {
            drawTankImg(g);
            return;
        }
        move();
        drawTankImg(g);
    }

    private void drawTankImg(Graphics g) {
        switch (dir) {
            case UP:
                g.drawImage(ResourceMgr.T_UP,x,y,null);
                //获取不同方向上的tank宽高
                T_WEIGHT = ResourceMgr.T_UP.getWidth();
                T_HEIGHT = ResourceMgr.T_UP.getHeight();
                break;
            case DOWN:
                g.drawImage(ResourceMgr.T_DOWN,x,y,null);
                //获取不同方向上的tank宽高
                T_WEIGHT = ResourceMgr.T_DOWN.getWidth();
                T_HEIGHT = ResourceMgr.T_DOWN.getHeight();
                break;
            case LEFT:
                g.drawImage(ResourceMgr.T_LEFT,x,y,null);
                //获取不同方向上的tank宽高
                T_WEIGHT = ResourceMgr.T_LEFT.getWidth();
                T_HEIGHT = ResourceMgr.T_LEFT.getHeight();
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.T_RIGHT,x,y,null);
                //获取不同方向上的tank宽高
                T_WEIGHT = ResourceMgr.T_RIGHT.getWidth();
                T_HEIGHT = ResourceMgr.T_RIGHT.getHeight();
                break;
            default:
                break;
        }
    }

    public void move(){
        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            default:
                break;
        }
    }

    public void fire() {
//        计算子弹发射的位置
        int bulletX = this.x + Tank.T_WEIGHT/2 - Bullet.B_WEIGHT/2;
        int bulletY = this.y + Tank.T_HEIGHT/2 - Bullet.B_HEIGHT/2;
        tankFrame.getBullets().add(new Bullet(bulletX,bulletY,this.getDir(),Group.BAD,tankFrame));
    }

    public void die() {
        alive = false;
    }
}
