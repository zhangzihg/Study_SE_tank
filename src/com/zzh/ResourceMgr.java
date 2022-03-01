package com.zzh;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    public static BufferedImage T_UP,T_DOWN,T_LEFT,T_RIGHT;
    public static BufferedImage B_UP,B_DOWN,B_LEFT,B_RIGHT;
    public static  BufferedImage[] Boom = new BufferedImage[11];

    static {
        try {
//            初始化从磁盘读取坦克图片资源
            T_UP = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            T_DOWN = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            T_LEFT = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            T_RIGHT = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));

//            初始化从磁盘读取子弹图片
            B_UP = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            B_DOWN = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            B_LEFT = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            B_RIGHT = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));

//            初始化从磁盘读取爆炸图片
                for (int i=0; i<Boom.length; i++){
                    Boom[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/" +i+".gif" ));
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
