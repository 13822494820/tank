package com.mashibing.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * awt没用，但用来做载体做学习其他知识
 * @author a123dsa1
 *
 */
public class T {
	public static void main(String[] args) throws Exception {
		TankFrame tf = new TankFrame();
		
		//初始化敌方tank
		for(int i=0;i<5;i++) {
			tf.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN,Group.BAD, tf));
		}
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();  //repaint先调用update，再调用paint
		}
	}
}
