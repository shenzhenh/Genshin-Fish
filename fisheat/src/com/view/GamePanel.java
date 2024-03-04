package com.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.model.BadFish;
import com.model.Fish;
import com.utils.Dir;
import com.utils.Picture;

public class GamePanel extends JPanel implements ActionListener{
	GamePanel gamePanel=this;
	private JFrame mainFrame=null;
	public Fish myFish = null;
    public ArrayList<BadFish> badFishss = new ArrayList<BadFish>();
    private String gameFlag="start";
    int time = 0;
	//构造里面初始化相关参数
	public GamePanel(JFrame frame){
		this.setLayout(null);
		mainFrame = frame;
		
		//菜单
		initMenu();
		
		//创建我的鱼
		initMyFish();
		
		//创建敌鱼
		initBadFishs();
		//启动主线程
		new Thread(new GameThread()).start();
		
		//添加键盘事件监听
		createKeyListener();
	}
	
	//添加键盘监听
	private void createKeyListener() {
		KeyAdapter l = new KeyAdapter() {
			//按下
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
					//向上
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						if(myFish!=null){
							myFish.move(Dir.UP);
						}
						break;
						
					//向右	
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						if(myFish!=null){
							myFish.move(Dir.RIGHT);
						}
						break;
						
					//向下
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						if(myFish!=null){
							myFish.move(Dir.DOWN);
						}
						break;
						
					//向左
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						if(myFish!=null){
							myFish.move(Dir.LEFT);
						}
						break;
				}
			
			}
			//松开
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		};
		//给主frame添加键盘监听
		mainFrame.addKeyListener(l);
	}
	//游戏线程，用来自动下移
	private class GameThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				if("start".equals(gameFlag)){
					repaint();
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//创建坏鱼
	private void createBadFish(){
    	BadFish badFish = new BadFish(this,"BAD");
        badFishss.add(badFish) ;
    }
	//初始创建12只
	private void initBadFishs() {
        for (int i = 0; i < 12; i++) {
        	createBadFish();
        }
    }
	
	private void initMyFish() {
		myFish = new Fish(0, 320, Dir.RIGHT, this, "GOOD");		
	}
	@Override
	public void paint(Graphics g) {
    	super.paint(g);
    	//绘制背景图
    	g.drawImage(Picture.bg0, 0,0,null);
    	//绘制鱼
        if(myFish!=null) myFish.paint(g);
        
        //绘制敌方鱼
        for (int i = 0; i < badFishss.size(); i++) {
        	 BadFish fish = badFishss.get(i);
        	 if(fish!=null)
        		 fish.paint(g);
		}
        if("start".equals(gameFlag)){
    		if(time==30){
        		time=0;
        		if(badFishss.size()<60){
        			createBadFish();
        		}
        	}else {
    			time++;
    		}
    	}
    }
	//初始化按钮
	private void  initMenu(){
		// 创建菜单及菜单选项
		JMenuBar jmb = new JMenuBar();
		JMenu jm1 = new JMenu("游戏");
		jm1.setFont(new Font("思源宋体", Font.BOLD, 15));// 设置菜单显示的字体
		
		JMenuItem jmi1 = new JMenuItem("开始新游戏");
		JMenuItem jmi2 = new JMenuItem("退出");
		jmi1.setFont(new Font("思源宋体", Font.BOLD, 15));
		jmi2.setFont(new Font("思源宋体", Font.BOLD, 15));
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		
		jmb.add(jm1);
		mainFrame.setJMenuBar(jmb);// 菜单Bar放到JFrame上
		
		jmi1.addActionListener(this);
		jmi1.setActionCommand("Restart");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
	}
	
	public boolean isHit(Fish fish,BadFish badFish) {
		if(isHit1(fish, badFish)){//如果在碰撞范围内
			if(fish.getX()<badFish.getX()){//鱼在敌鱼的左边
				if(fish.getY()>badFish.getY()){//鱼在敌鱼的下方
					if((fish.getY()+10<badFish.getY()+badFish.getHeight())
							&& (fish.getX() + fish.getWidth()>badFish.getX()+10)){
						return true;
					}
				}else if(fish.getY()<badFish.getY()){//鱼在敌鱼的上方
					if(badFish.getY()+10<fish.getY()+fish.getHeight()
							&&fish.getX() + fish.getWidth()>badFish.getX()+10){
						return true;
					}
				}
			}else if(fish.getX()>badFish.getX()){//鱼在敌鱼的左边
				if(fish.getY()>badFish.getY()){//鱼在敌鱼的下方
					if(fish.getY()+10<badFish.getY()+badFish.getHeight()
							&& badFish.getX() + badFish.getWidth()>fish.getX()+10){
						return true;
					}
				}else if(fish.getY()<badFish.getY()){//鱼在敌鱼的上方
					if(badFish.getY()+10<fish.getY()+fish.getHeight()
							&& badFish.getX() + badFish.getWidth()>fish.getX()+10){
						return true;
					}
				}
			}
		}
		return false;
	}
	//判断鱼是否碰撞
	public boolean isHit1(Fish fish,BadFish badFish) {
		//方式1
		//左上角
		int x1 = badFish.getX();
		int y1 = badFish.getY();
		//右上角
		int x2 = badFish.getX()+badFish.getWidth();
		int y2 = badFish.getY();
		//右下角
		int x3 = badFish.getX()+badFish.getWidth();
		int y3 = badFish.getY()+badFish.getHeight();
		//左下角
		int x4 = badFish.getX();
		int y4 = badFish.getY()+badFish.getHeight();
		//只要有一个点在范围内，则判断为碰撞
		if(comparePointFish(x1,y1,fish)|| comparePointFish(x2,y2,fish)
				||comparePointFish(x3,y3,fish)||comparePointFish(x4,y4,fish) ){
			return true;
		}
		
		//方式1没成立则用方式2判断
		//方式2
		x1 = fish.getX();
		y1 = fish.getY();
		//右上角
		x2 = fish.getX()+fish.getWidth();
		y2 = fish.getY();
		//右下角
		x3 = fish.getX()+fish.getWidth();
		y3 = fish.getY()+fish.getHeight();
		//左下角
		x4 = fish.getX();
		y4 = fish.getY()+fish.getHeight();
		if(comparePoint(x1,y1,badFish)|| comparePoint(x2,y2,badFish)||comparePoint(x3,y3,badFish)||comparePoint(x4,y4,badFish) ){
			return true;
		}
		return false;
	}
	//用坐标来判断
	private boolean comparePointFish(int x,int y,Fish fish){
		//大于左上角，小于右下角的坐标则肯定在范围内
		if(x>fish.getX() && y >fish.getY()
			&& x<fish.getX()+fish.getWidth() && y <fish.getY()+fish.getHeight()	){
			return  true;
		}
		return false;
	}
	//用坐标来判断
	private boolean comparePoint(int x,int y,BadFish badFish){
		//大于左上角，小于右下角的坐标则肯定在范围内
		if(x>badFish.getX() && y >badFish.getY()
			&& x<badFish.getX()+badFish.getWidth() && y <badFish.getY()+badFish.getHeight()){
			return  true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.ITALIC, 18)));
		if ("Exit".equals(command)) {
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.exit(0);
			} 
		}else if("Restart".equals(command)){
			restart();
		}		
	}
	//重新开始
	private void restart() {

		myFish = null;
		
	    badFishss.clear();
	    
		initMyFish();
		initBadFishs();
		
		//游戏开始标记
		gameFlag="start";
	
	}
	
	//游戏结束
	public void gameOver() {
		gameFlag = "end";
		
		for (int i = 0; i < badFishss.size(); i++) {
	       	 BadFish fish = badFishss.get(i);
	       	 if(fish!=null){
	       		 fish.setLiving(false);
	       	 }
	  	}
		badFishss.clear();
		
		//弹出结束提示
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
	    JOptionPane.showMessageDialog(mainFrame, "你失败了,请再接再厉!");
	}
}
