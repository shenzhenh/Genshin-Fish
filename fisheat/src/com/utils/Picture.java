package com.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Picture {
    public static BufferedImage bg0,FishL,FishR,Fishl,Fishr;
    
    public static List badFishImages =new ArrayList() ;
    public static List badFishImages1 =new ArrayList() ;
    //静态代码块---程序启动，在加载类的时候，就会执行
    static {
        try {
            bg0 = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/bg0.jpg"));
            FishL = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIcon0.png"));
            FishR = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIcon0R.png"));
            Fishl = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIcon.png"));
            Fishr = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIconR.png"));
            BufferedImage temp;
            for (int i = 1; i <= 18; i++) {
            	temp = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIcon"+i+".png"));
            	badFishImages.add(temp);
            	temp = ImageIO.read(Picture.class.getClassLoader().getResourceAsStream("img/fishIconL"+i+".png"));
            	badFishImages1.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
