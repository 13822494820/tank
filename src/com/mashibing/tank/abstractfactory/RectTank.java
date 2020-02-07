package com.mashibing.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.mashibing.tank.Audio;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.DefaultFireStrategy;
import com.mashibing.tank.Dir;
import com.mashibing.tank.FireStrategy;
import com.mashibing.tank.Group;
import com.mashibing.tank.PropertyMgr;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.abstractfactory.BaseTank;

/**
 * 封装成一个tank类
 * 因为可能需要多个tank操作，这时只有tankFrame的x，y是不够的，只会越来越多代码
 * java的封装性可以处理这个情况
 * @author a123dsa1
 *
 */
public class RectTank extends BaseTank{
	public static int WIDTH = ResourceMgr.goodtankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodtankU.getHeight();
    int x,y;
	Dir dir = Dir.DOWN;
	private static final int SPEED = 2;
	Group group = Group.BAD;
	
	//判断是否移动，处理stop的状态
	private boolean moving = true;
	
	private boolean living = true;
	
	public Rectangle rect = new Rectangle();
	
	TankFrame tf = null;
	
	private Random random = new Random();
	
	FireStrategy fs;
	
	public RectTank(int x, int y, Dir dir,Group group, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		if(group == Group.GOOD) {
			String goodFsName = (String)PropertyMgr.get("goodFs");
			try {
				fs = (FireStrategy)Class.forName(goodFsName).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			fs = new DefaultFireStrategy();
		}
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
		
		Color c = g.getColor();
		g.setColor(group==group.GOOD?Color.RED:Color.blue);
		g.fillRect(x, y, 40, 40);
		g.setColor(c);
		move();
	}

	private void move() {
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
		
		boundsCheck();
		
		//update rect
		rect.x =this.x;
		rect.y =this.y;
	}

	private void boundsCheck() {
		if(this.x < 2)
			x = 2;
		if(this.y < 28)
			y = 28;
		if(this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH - 2)
			x = TankFrame.GAME_WIDTH - RectTank.WIDTH - 2;
		if(this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2)
			y = TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2;
	}


	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}


	//fire可以reture子弹，但只能画一个子弹，不灵活
	public void fire() {
		
		//fs.fire(this);
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		Dir[] dirs = Dir.values();
		for(Dir dir : dirs) {
			tf.gf.createBullet(bX,bY,dir,group, tf);
		}
		
		if(group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
	
	}

	public void die() {
		this.living = false;
		
	}
	
	

	
}
