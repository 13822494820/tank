package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mashibing.tank.decorator.RectDecorator;
import com.mashibing.tank.decorator.TailDecorator;

import observer.TankFireEvent;
import observer.TankFireHandler;
import observer.TankFireObserver;

/**
 * 封装成一个tank类
 * 因为可能需要多个tank操作，这时只有tankFrame的x，y是不够的，只会越来越多代码
 * java的封装性可以处理这个情况
 * @author a123dsa1
 *
 */
public class Tank extends GameObject{
	public static int WIDTH = ResourceMgr.goodtankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodtankU.getHeight();
	int oldx, oldy;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 2;
	private Group group = Group.BAD;
	
	//判断是否移动，处理stop的状态
	private boolean moving = true;
	
	private boolean living = true;
	
	public Rectangle rect = new Rectangle();
	
	private Random random = new Random();
	
	
	public Tank(int x, int y, Dir dir,Group group) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		GameModel.getInstance().add(this);
		
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	public Rectangle getRect() {
		return rect;
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
			GameModel.getInstance().remove(this);
		
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
		
		move();
	}

	private void move() {
		oldx = x;
		oldy = y;
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
		if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2)
			x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2)
			y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
	}


	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}


	//fire可以reture子弹，但只能画一个子弹，不灵活
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		new Bullet(bX,bY,this.dir,this.group);
		//有bug new Bullet把自己加了一遍	 
//		GameModel.getInstance().add(new RectDecorator(
//				new TailDecorator(
//				new Bullet(bX,bY,this.dir,this.group))));
		
		if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
	}

	public void die() {
		this.living = false;
		
	}

	public void stop() {
		moving = false;
	}
	
	public void back() {
		x= oldx;
		y = oldy;
	}

	List<TankFireObserver> fireObservers = new ArrayList<>();
	
	public void handleFireKey() {
		System.out.println(fireObservers.size());
		//添加观察者
		//fireObservers.add(new TankFireHandler());
		TankFireEvent event = new TankFireEvent(this);
		for(TankFireObserver o : fireObservers) {
			o.actionOnFire(event);
		}
	}
	
	

	
}
