package com.mashibing.tank;

import java.awt.Graphics;

/**
 * 封装成一个tank类
 * 因为可能需要多个tank操作，这时只有tankFrame的x，y是不够的，只会越来越多代码
 * java的封装性可以处理这个情况
 * @author a123dsa1
 *
 */
public class Tank {
	private int x,y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 10;
	
	public Tank(int x, int y, Dir dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public void paint(Graphics g) {
		g.fillRect(x, y, 50, 50);
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

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	

	
}
