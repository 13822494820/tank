package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Explode {
	private static final int SPEED = 10;
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	private int x, y;

	private TankFrame tf = null;
	
    //boolean living = true;
    private int step = 0;
	
	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf= tf;
		
		//Audio.main(null);
	}
	
	

	public void paint(Graphics g) {
		
		g.drawImage(ResourceMgr.explodes[step++], x ,y, null);
		if(step >= ResourceMgr.explodes.length)
			tf.explodes.remove(this);
		
	}
	
	

	
	
	
}
