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
		//根据物体移动变化，x,y不断变化，以便后面的装饰类可以取到动态的x和y
		this.x = go.x;
		this.y = go.y;
		go.paint(g);
		
		Color c = g.getColor();
		g.setColor(Color.yellow);
		//防止被图片覆盖
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
