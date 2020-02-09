package com.mashibing.tank.decorator;

import java.awt.Graphics;

import com.mashibing.tank.GameObject;

public abstract class GoDecorator extends GameObject{
	GameObject go;
	
	public GoDecorator(GameObject go) {
		this.go = go;
	}

	public abstract void paint(Graphics g) ;
	
}
