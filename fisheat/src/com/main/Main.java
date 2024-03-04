package com.main;

import com.view.FishFrame;
import com.view.GamePanel;

public class Main {
    public static void main(String[] args) {
    	new Play0("src\\music\\bgm.mp3").start();
    	FishFrame frame = new FishFrame();
		GamePanel panel = new GamePanel(frame);
		frame.add(panel);
		frame.setVisible(true);
    }
}