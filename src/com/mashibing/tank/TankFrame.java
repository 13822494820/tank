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
		//x+=10;
		//y+=10;
	}
	
	
	//定义一个监听键盘的类，使得通过按键使矩形移动
	class MyKeyListener extends KeyAdapter{

		//用boolean表示按下的键,这样方向可以组合
		//有可能同时按下上下键，但在计算机时有一个先后顺序的，即使看上去同时
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		//键被按下时调用
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			//这里不能斜着移动，因为switch一次改一次
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}
			repaint();
		}

		//键松开时调用
		@Override
		public void keyReleased(KeyEvent e) {
			//键抬起时，恢复
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;

			default:
				break;
			}
			
		}
		
	}
}
