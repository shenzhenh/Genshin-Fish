package com.view;

import java.awt.BorderLayout;
import java.util.concurrent.CompletableFuture;
import javax.swing.JFrame;

public class FishFrame extends JFrame {
    public int window_Width = 1180, window_Height = 640;
 //   CompletableFuture c = new CompletableFuture();


    public FishFrame() {
        setTitle("科斯魔");//窗口标题
        setSize(window_Width, window_Height);//窗口分辨率
        setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭按钮是关闭程序
        setLocationRelativeTo(null);   //设置居中
    	setResizable(false); //不允许修改界面大小
        setVisible(true);//窗口是否可以显示 true---false
    }
}
