package com.mashibing.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.mashibing.tank.GameObject;

public class TailDecorator extends GoDecorator{

	public TailDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {
		//���������ƶ��仯��x,y���ϱ仯���Ա�����װ�������ȡ����̬��x��y
		this.x = go.x;
		this.y = go.y;
		go.paint(g);
		
		Color c = g.getColor();
		g.setColor(Color.yellow);
		//��ֹ��ͼƬ����
		g.drawLine(go.x, go.y, 
				go.x + getWidth(), go.y + getHeight());
		g.setColor(c);
	}

	@Override
	public int getWidth() {
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		return super.go.getHeight();
	}
}
