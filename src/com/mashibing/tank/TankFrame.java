package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//继承Frame
public class TankFrame extends Frame{
	Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
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
	
	/**
	 * 双缓存解决闪烁
	 * 闪烁是因为在显示大图时，部分图像还没计算完，
	 * 有部分图像算完显示在屏幕上了
	 * 解决：在内存定义同样大小的空间，先画在内存，画完再一次性画在屏幕上
	 */
	Image offScreenImage = null;   //定义一张图片，在内存
	@Override
	public void update(Graphics g) {  //g是屏幕的画笔
		if(offScreenImage == null)
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		Graphics gOffScreen = offScreenImage.getGraphics();   //获取画笔
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);   //重画背景
		gOffScreen.setColor(c);
		paint(gOffScreen);  //传递当前画笔，画在内存的
		g.drawImage(offScreenImage,0,0,null);   //图片一次性画在屏幕
	}
	
	//每次绘制会清空后再画，最小化再显示也会再绘制
	@Override
	public void paint(Graphics g) {	
		Color c = g.getColor();
		g.setColor(Color.white);
		g.drawString("子弹的数量" + bullets.size(), 10, 60);
		g.drawString("敌人的数量" + tanks.size(), 10, 80);
		g.drawString("爆炸的数量" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		
		//list用这个不能删除，会出现java.util.ConcurrentModificationException
		//因为内部状态不一致
//		for(Bullet b: bullets) {
//			b.paint(g);
//		}
		for(int i=0;i<bullets.size();i++) {
			bullets.get(i).paint(g);
		}
		
		for(int i=0;i<tanks.size();i++) {
			tanks.get(i).paint(g);
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			for(int j=0;j<tanks.size();j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
		//方法二
//		for(Iterator<Bullet> it =bullets.iterator();it.hasNext();) {
//			Bullet b = it.next();
//			if(!b.live) 
//				it.remove();
//		}
		
		for(int i=0;i<explodes.size();i++) {
			explodes.get(i).paint(g);
		}
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

			}
			
			setMainTankDir();
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
			case KeyEvent.VK_CONTROL:
				myTank.fire();
				break;
				
			}
			
			setMainTankDir();
		}

		private void setMainTankDir() {
			if(!bL && !bU &&!bR && !bD)
				myTank.setMoving(false);
			else
				myTank.setMoving(true);
			if(bL) myTank.setDir(Dir.LEFT);
			if(bU) myTank.setDir(Dir.UP);
			if(bR) myTank.setDir(Dir.RIGHT);
			if(bD) myTank.setDir(Dir.DOWN);
		}
		
	}
}
