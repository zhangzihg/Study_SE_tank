package com.zzh;

import java.awt.*;

public class Bullet {
    private int x,y;
    private boolean live = true;
    private Dir dir = Dir.DOWN;
    private Group group;
    private static final int B_SPEED = 11;
    public static  int B_HEIGHT = ResourceMgr.B_DOWN.getHeight();
    public static   int B_WEIGHT = ResourceMgr.B_DOWN.getWidth();
    //将Reactangle的创建从碰撞检测的方法中拿出来，如果在里面，每画一次，都要产生2*n*m个对象，对性能影响太大
    private Rectangle rectangle = new Rectangle();

    private TankFrame tankFrame = null;

    public Bullet(int x,int y,Dir dir,TankFrame tankFrame){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame =tankFrame;
    }

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        rectangle.x = this.x;
        rectangle.y = this.y;
        System.out.println("子弹初始化的值 " + rectangle.x + " ========= " + rectangle.y);
        rectangle.height = Bullet.B_HEIGHT;
        rectangle.width = Bullet.B_WEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        move();
        //子弹飞出边界就算死亡
        if(x < -Bullet.B_WEIGHT || x > TankFrame.GAME_WEIGHT || y < -Bullet.B_HEIGHT || y > TankFrame.GAME_HEIGHT){
            System.out.println("++++++++++++++++===");
            setLive(false);
            return;
        }
        drawBulletImg(g);
//        if(!live) {
//            tankFrame.getBullets().remove(this);
//            return;
//        }
//        Color color = g.getColor();
//        g.setColor(Color.yellow);
//        g.fillOval(x, y, B_WEIGHT,B_HEIGHT);
//        g.setColor(color);
    }

    public void move(){
        switch (dir) {
            case UP:
                y -= B_SPEED;
                break;
            case DOWN:
                y += B_SPEED;
                break;
            case LEFT:
                x -= B_SPEED;
                break;
            case RIGHT:
                x += B_SPEED;
                break;
            default:
                break;
        }

//        移动时更新rectangle的坐标
        rectangle.x = this.x;
        rectangle.y = this.y;
        System.out.println("子弹更新后的值 " + rectangle.x + " ========= " + rectangle.y);


    }

    public void drawBulletImg(Graphics g){
        switch (dir) {
            case UP:
                g.drawImage(ResourceMgr.B_UP,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.B_DOWN,x,y,null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.B_LEFT,x,y,null);
                //获取不同方向上的tank宽高
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.B_RIGHT,x,y,null);
                break;
            default:
                break;
        }
    }

    public void collisingWithTank(Tank tank) {
        //如果是同一阵营打出的字段不做碰撞检测
        if(this.group == tank.getGroup()) return;

//        if(this.live && tank.isAlive()){
            if( rectangle.intersects(tank.getRectangle())){

                //爆炸发生的位置,发生在坦克中心
//                int boom_x = this.x - (this.getX()-tank.getX())/2;
//                int boom_y = this.y - (this.getY()-tank.getY())/2;

                int boom_x = tank.getX() + (tank.T_WEIGHT-Bullet.B_WEIGHT)/2;
                int boom_y = tank.getY() + (tank.T_HEIGHT-Bullet.B_HEIGHT)/2;
                //如果碰撞了，子弹死亡，坦克死亡
                die();
                tank.die();
//                发生爆炸
                this.tankFrame.getBooms().add(new Boom(boom_x,boom_y,tankFrame));
            }
//        }

    }

    private void die() {
        this.live = false;
    }
}
