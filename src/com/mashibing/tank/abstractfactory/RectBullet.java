package com.mashibing.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Explode;
import com.mashibing.tank.Group;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.abstractfactory.BaseBullet;

public class RectBullet extends BaseBullet{
	private static final int SPEED = 6;
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();
	private int x, y;
	private Dir dir;
	TankFrame tf = null;
	private Group group = Group.BAD;
	
	Rectangle rect = new Rectangle();
	
    boolean living = true;
	
	public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf= tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		tf.bullets.add(this);
	}
	
	public Group getGroup() {
		return group;
	}



	public void setGroup(Group group) {
		this.group = group;
	}

	public void paint(Graphics g) {
		if(!living) {
			tf.bullets.remove(this);
		}
		
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillRect(x, y, 20, 20);
		g.setColor(c);
		
		move();
	}
	
	private void move() {		
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

		}
		
		//update rect 
		rect.x = this.x;
		rect.y = this.y;
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
			living = false;
	}

	public void collideWith(BaseTank tank) {
		if(this.group == tank.getGroup())
			return;
		
		//可以用一个rect记录子弹位置,有内存溢出问题
//		Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
//		Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.getWIDTH(),Tank.getHEIGHT());
		if(rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			
			int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
			tf.explodes.add(tf.gf.createExplode(eX, eY, tf));
		}
	}

	private void die() {
		this.living = false;
	}
	
	
}
