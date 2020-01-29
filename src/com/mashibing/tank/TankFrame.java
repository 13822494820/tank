package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//继承Frame
public class TankFrame extends Frame{
	
	public TankFrame() {
		setSize(800, 600);
		setResizable(false);  //是否可以改变大小
		setTitle("tank war");
		setVisible(true);
		
		//添加监听器，现在监听着closing这件事
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
	
	//每次绘制会清空后再画，最小化再显示也会再绘制
	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
	}
}
