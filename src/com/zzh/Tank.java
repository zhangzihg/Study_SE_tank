package com.zzh;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private final static int SPEED = 5;
    private boolean moving = true;
    private TankFrame tankFrame = null;
    private boolean alive = true;
    private Group group;
    private Rectangle rectangle = new Rectangle();
    private Random random = new Random();
    public static  int T_WEIGHT = ResourceMgr.T_UP.getHeight();
    public static  int T_HEIGHT  = ResourceMgr.T_UP.getHeight();
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

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.height = Tank.T_HEIGHT;
        rectangle.width = Tank.T_WEIGHT;
    }

    public Tank(int x, int y, Dir dir, boolean moving, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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
//        敌方坦克移动开火
        if(random.nextInt(100) > 95 &&  this.getGroup() == Group.BAD) this.fire();
//        敌方坦克移动一段时间久换方向
        if(random.nextInt(100) > 90 &&  this.getGroup() == Group.BAD) this.randomDir();

//        坦克移动边界检测
        checkBounds();
        System.out.println("tank move tankmove");
        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    private void checkBounds() {
        if(x<2) x=2;
        if(x>TankFrame.GAME_WEIGHT - Tank.T_WEIGHT -8) x=TankFrame.GAME_WEIGHT - Tank.T_WEIGHT -8;
        if(y<28) y=28;
        if(y > TankFrame.GAME_HEIGHT - TankFrame.HEIGHT - 60) y=TankFrame.GAME_HEIGHT - TankFrame.HEIGHT - 60;
    }

    public void randomDir(){
        dir = dir.values()[random.nextInt(4)];
    }

    public void fire() {
//        计算子弹发射的位置
        int bulletX = this.x + Tank.T_WEIGHT/2 - Bullet.B_WEIGHT/2;
        int bulletY = this.y + Tank.T_HEIGHT/2 - Bullet.B_HEIGHT/2;
        tankFrame.getBullets().add(new Bullet(bulletX,bulletY,this.getDir(),this.getGroup(),tankFrame));
    }

    public void die() {
        alive = false;
    }
}
