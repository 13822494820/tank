package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//继承Frame
public class TankFrame extends Frame{
	int x=200,y=200;
	
	public TankFrame() {
		setSize(800, 600);
		setResizable(false);  //是否可以改变大小
		setTitle("tank war");
		setVisible(true);
		
		this.addKeyListener(new MyKeyListener());
		
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
		g.fillRect(x, y, 50, 50);
		x+=10;
		y+=10;
	}
	
	
	//定义一个监听键盘的类，使得通过按键使矩形移动
	class MyKeyListener extends KeyAdapter{

		//键被按下时调用
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("key pressed");
		}

		//键松开时调用
		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("key released");
		}
		
	}
}
