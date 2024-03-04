package com.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.utils.Picture;
import com.view.GamePanel;

public class BadFish {
	private int x, y, width = 80, height = 80, speed = 1;
	private boolean isMoving = false, isLiving = true;
	private GamePanel panel;// 相互持有
	private String group;
	private BufferedImage image;
	private int large = 0;// 0表示小的，1表示大一号
	private int type = 0;// 1-18 表示不同的鱼
	private List imgList = null;

	public BadFish(GamePanel panel, String group) {
		this.panel = panel;
		this.group = group;
		
		init();

		move();
	}

	private void init() {
		this.x=1180;
    	Random random = new Random();
    	this.type= random.nextInt(18)+1;
    	this.large = random.nextInt(2)==0? 0:1;
    	if(large==1){
			imgList = Picture.badFishImages1;
		}else {
			imgList = Picture.badFishImages;
		}
    	image = (BufferedImage)imgList.get(type-1);
        width = image.getWidth();
        height = image.getHeight();
      	y = random.nextInt(590-height);
	}

	private void move() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isLiving) {
					
					hit();
					
					x -= speed;
					if (x + width < 0) {
						x = 1180;
						y = new Random().nextInt(590 - height);
					}
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!isLiving){
						break;
					}
				}
			}
		}).start();
	}
	
	private void hit(){
		Fish fish = panel.myFish;
		if(fish==null) return;
		if(!isLiving) return ;
		
		if(panel.isHit(fish, this)){//鱼碰撞
			if(fish.getHeight()>=this.getHeight()){//吃掉敌鱼
				System.out.println("吸收能量....");
				//鱼变大一点
				if(fish.getWidth()<150){
					fish.setHeight(fish.getHeight()+2);
					fish.setWidth(fish.getWidth()+3);
				}
				//敌鱼消失
				this.remove();
			}else {//被吃掉弹出失败
				System.out.println("被敌人击败....");
				fish.setMoving(false);
				fish.setLiving(false);
				panel.myFish=null;
				
				panel.gameOver();
			}
		}
	}

	public void paint(Graphics g) {
		if (!isLiving) {
			 ArrayList<BadFish> badFishs = panel.badFishss;
			 badFishs.remove(this);
			return;
		}
		g.drawImage(image, x, y, width, height, null);
	}
	

	public void remove() {
		this.isMoving=false;
		this.isLiving=false;
		panel.badFishss.remove(this);
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isLiving() {
		return isLiving;
	}

	public void setLiving(boolean isLiving) {
		this.isLiving = isLiving;
	}
	
}
