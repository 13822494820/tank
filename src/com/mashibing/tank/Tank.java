package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * 封装成一个tank类
 * 因为可能需要多个tank操作，这时只有tankFrame的x，y是不够的，只会越来越多代码
 * java的封装性可以处理这个情况
 * @author a123dsa1
 *
 */
public class Tank {
	public static int WIDTH = ResourceMgr.goodtankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodtankU.getHeight();
	private int x,y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 2;
	private Group group = Group.BAD;
	
	//判断是否移动，处理stop的状态
	private boolean moving = true;
	
	private boolean living = true;
	
	private TankFrame tf;
	
	private Random random = new Random();
	
	public Tank(int x, int y, Dir dir,Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
	}
	

	public static int getWIDTH() {
		return WIDTH;
	}


	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}


	public static int getHEIGHT() {
		return HEIGHT;
	}


	public static void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}


	public Group getGroup() {
		return group;
	}



	public void setGroup(Group group) {
		this.group = group;
	}



	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
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

	public void paint(Graphics g) {
		if(!living)
			tf.tanks.remove(this);
		
		switch (dir) {
		case LEFT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodtankL:ResourceMgr.badtankL, x, y, null);
			break;
		case UP:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodtankU:ResourceMgr.badtankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodtankR:ResourceMgr.badtankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodtankD:ResourceMgr.badtankD, x, y, null);
			break;
		default:
			break;
		}
		
		moving();
	}

	private void moving() {
		if(!moving)
			return;
		
		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		default:
			break;
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95)
			this.fire();
		
		if(this.group == Group.BAD && random.nextInt(100) > 95)
			randomDir();
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}


	//fire可以reture子弹，但只能画一个子弹，不灵活
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT;
		tf.bullets.add(new Bullet(bX,bY,this.dir,this.group, this.tf));
	}

	public void die() {
		this.living = false;
		
	}
	
	

	
}
