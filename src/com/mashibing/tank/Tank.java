package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 封装成一个tank类
 * 因为可能需要多个tank操作，这时只有tankFrame的x，y是不够的，只会越来越多代码
 * java的封装性可以处理这个情况
 * @author a123dsa1
 *
 */
public class Tank {
	public static int WIDTH = ResourceMgr.tankD.getWidth();
	public static int HEIGHT = ResourceMgr.tankD.getHeight();
	private int x,y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 5;
	
	//判断是否移动，处理stop的状态
	private boolean moving = false;
	
	private boolean living = true;
	
	private TankFrame tf;
	
	public Tank(int x, int y, Dir dir, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
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
			g.drawImage(ResourceMgr.tankL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.tankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.tankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.tankD, x, y, null);
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
	}

	//fire可以reture子弹，但只能画一个子弹，不灵活
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT;
		tf.bullets.add(new Bullet(bX,bY,this.dir,this.tf));
	}

	public void die() {
		this.living = false;
		
	}
	
	

	
}
