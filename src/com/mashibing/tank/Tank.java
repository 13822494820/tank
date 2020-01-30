package com.mashibing.tank;

import java.awt.Graphics;

/**
 * ��װ��һ��tank��
 * ��Ϊ������Ҫ���tank��������ʱֻ��tankFrame��x��y�ǲ����ģ�ֻ��Խ��Խ�����
 * java�ķ�װ�Կ��Դ���������
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