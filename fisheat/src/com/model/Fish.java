package com.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.utils.Dir;
import com.utils.Picture;
import com.view.GamePanel;

public class Fish extends ArrayList<Fish> {
    private int x, y,/*涓昏楸煎嚭鐢熷潗鏍�*/
            width = 150, height = 90,
            speed = 20;

	private Dir dir;
    private boolean isMoving = false, isLiving = true;
    private GamePanel panel;//鐩镐簰鎸佹湁
    private String group;
    private Graphics g;
    private BufferedImage image=Picture.FishL;
    private int type = 1;//1 鍙筹紝 -1 宸� 
    private int outcome=1;
    
    public Fish(int x, int y, Dir dir, GamePanel panel, String group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.panel = panel;
        this.group = group;
    }

    public void paint(Graphics g) {
        this.g = g;
        switch (dir) {
            case UP:
            	image = type==1?Picture.FishR:Picture.FishL;
                break;
            case DOWN:
            	image = type==1?Picture.FishR:Picture.FishL;
                break;
            case LEFT:
            	type = -1;
            	image = type==1?Picture.FishR:Picture.FishL;
                break;
            case RIGHT:
            	type = 1;
            	image = type==1?Picture.FishR:Picture.FishL;
                break;
        }
        eat();
        if (outcome==0) {
		image = outcome==0?Picture.Fishr:Picture.Fishl;
		outcome=1;
		g.drawImage(image, x, y,width,height, null);
		}
        image = type==1?Picture.FishR:Picture.FishL;
        g.drawImage(image, x, y,width,height, null);
    }

    public void move(Dir dir) {
    	this.setDir(dir);
    	this.setMoving(true);
         
        if (!isMoving) {
            return;
        }
     
        switch (dir) {
            case UP:
                y -= speed;
                if(y<=0){
                	y=0;
                }
                break;
            case DOWN:
                y += speed;
                if(y>=590-height){
                	y=590-height;
                }
                break;
            case LEFT:
                x -= speed;
                if(x<=0){
                	x=0;
                }
                break;
            case RIGHT:
                x += speed;
                if(x>=1180-width){
                	x=1180-width;
                }
        }
        //鍚�
        eat();
    }

    private void eat() {
    	ArrayList<BadFish> badFishList = panel.badFishss;
    	BadFish badFish;
    	for (int i = 0; i < badFishList.size(); i++) {
    		badFish = badFishList.get(i);
    		if(!badFish.isLiving()) continue ;
    		if(panel.isHit(this, badFish)){//楸肩鎾�
    			if(this.height>=badFish.getHeight()){//鍚冩帀鏁岄奔
    				outcome=0;
    				System.out.println("击败");
    				System.out.println("this.width==="+this.width);
    				//楸煎彉澶т竴鐐�
    				if(this.width<890){
    					this.height+=6;
        				this.width +=6*1.66;
    				}
    				//鏁岄奔娑堝け
    				badFish.remove();
    			}else {//琚悆鎺夊脊鍑哄け璐�
    				System.out.println("死亡");
    				
    				panel.myFish.isMoving=false;
    				panel.myFish.isLiving=false;
    				panel.myFish=null;
    				
    				panel.gameOver();
				}
    			break;
    		}
		}
	}

	public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public int size() {
        return 0;
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

}

