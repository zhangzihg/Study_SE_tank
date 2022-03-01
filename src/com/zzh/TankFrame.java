package com.zzh;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class TankFrame extends Frame {
    public static final int GAME_WEIGHT = 800;
    public static final int GAME_HEIGHT = 600;
    public static  int TANK_NUM = 5;
    private ArrayList<Tank> tanks = new ArrayList<>(5);
    private Tank mainTank = new Tank((GAME_WEIGHT - Tank.T_WEIGHT) / 2, (GAME_HEIGHT - Tank.T_HEIGHT) / 2, Dir.UP, this,Group.GOOD);
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Boom> booms = new ArrayList<Boom>();

    public ArrayList<Boom> getBooms() {
        return booms;
    }

    public void setBooms(ArrayList<Boom> booms) {
        this.booms = booms;
    }

    //创建游戏的界面
    public TankFrame() {
        setTitle("Tank War");
        setSize(GAME_WEIGHT, GAME_HEIGHT);
        setResizable(false);
        setLocation(550, 250);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new MyKeListener());

        //创建敌方tank
        for (int i=0; i<TANK_NUM; i ++){
            Tank tank = new Tank(30+i*60,90,Dir.DOWN,this,Group.BAD);
            tanks.add(tank);
        }

    }

    class MyKeListener extends KeyAdapter {
        boolean dir_L = false;
        boolean dir_R = false;
        boolean dir_U = false;
        boolean dir_D = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    System.out.println("up");
                    dir_U = true;
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("down");
                    dir_D = true;
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("left");
                    dir_L = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("right");
                    dir_R = true;
                    break;
                default:
                    break;
            }

            //根据按键判断出tank的运动方向,相当于tank的方向盘
            getTankMovingDir();
        }

        private void getTankMovingDir() {
            if (dir_R) mainTank.setDir(Dir.RIGHT);
            if (dir_L) mainTank.setDir(Dir.LEFT);
            if (dir_U) mainTank.setDir(Dir.UP);
            if (dir_D) mainTank.setDir(Dir.DOWN);
//            System.out.println("**" + mainTank.getDir());
//            System.out.println("&&&" + mainTank.isMoving());
            //通过按键判断tank是否是静止状态
            if (!dir_R && !dir_L && !dir_D && !dir_U) {
                System.out.println("mmmmmmmmmm");
                mainTank.setMoving(false);
            } else {
                mainTank.setMoving(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
//                    System.out.println("^^^up");
                    dir_U = false;
                    break;
                case KeyEvent.VK_DOWN:
//                    System.out.println("^^^down");
                    dir_D = false;
                    break;
                case KeyEvent.VK_LEFT:
//                    System.out.println("^^^left");
                    dir_L = false;
                    break;
                case KeyEvent.VK_RIGHT:
//                    System.out.println("^^^right");
                    dir_R = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    mainTank.fire();
                    break;
                default:
                    break;
            }
            getTankMovingDir();
        }
    }

    //内存中的画布
    Image offScreenImg = null;

    /**
     * 消除闪烁
     *
     * @param g
     */
//    @Override
//    public void update(Graphics g) {
//        if (offScreenImg == null) {
//            offScreenImg = this.createImage(GAME_WEIGHT,GAME_HEIGHT);
//        }
//        Graphics graphics = offScreenImg.getGraphics();
//        Color color = graphics.getColor();
//        graphics.setColor(Color.BLACK);
//        graphics.fillRect(0,0,GAME_WEIGHT,GAME_HEIGHT);
//        //在内存中画出要显示的图片
//        paint(graphics);
//        //将内存中画的img直接全部画到显存中，使其消除闪烁问题
//        g.drawImage(offScreenImg,0,0,null);
//    }

    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WEIGHT, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WEIGHT, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.white);
        g.drawString("当前子弹数为 " + bullets.size() + " ", 10, 60);
        g.setColor(color);

//        将系统画笔递给tank对象，让他把自己画出来
        if(mainTank.isAlive()) mainTank.paint(g);
//        画出敌人坦克
        for(Iterator<Tank> iter = tanks.iterator(); iter.hasNext();){
            Tank next = iter.next();
//            System.out.println("敌方坦克 ： " + next.isAlive());
            //移除已死亡的tank
            if(!next.isAlive()){
                iter.remove();
            }else {
                next.paint(g);
            }
        }
//        这里不能用加强For循环，因为无法获取到迭代器对象，无法利用迭代器删除，会在移除元素时报错 java.util.ConcurrentModificationException
//        for(Bullet bullet : bullets){
//              if (bullet.isLive()) bullet.paint(g);
//              else bullets.remove(bullet);
//        }


//        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
//            Bullet bullet = iterator.next();
//            if (bullet.isLive()) bullet.paint(g);
////            else bullets.remove(bullet); //不要用这种方式删除，会报错 java.util.ConcurrentModificationException,只能用迭代器去删除
//            else iterator.remove();
//        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);

            for(Tank tank: tanks){
                //碰撞检测只负责如果产生碰撞修改，子弹和tank为死亡状态
                b.collisingWithTank(tank);
            }
            //                添加主坦克的碰撞检测
            //                添加子弹和tank的碰撞检测，每个子弹和所有坦克的碰撞检测
            if (b.isLive()) {
                //画出子弹
                b.paint(g);
            } else {
                bullets.remove(b);
            }

//            画出每个子弹碰撞时产生的爆炸
            for(int j=0; j<booms.size(); j++){
                if(!booms.get(j).isHappen()){
                    booms.remove(booms.get(i));
                }else {
                    booms.get(j).paint(g);
                }

            }
        }


    }

    public Tank getMainTank() {
        return mainTank;
    }

    public void setMainTank(Tank mainTank) {
        this.mainTank = mainTank;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}
