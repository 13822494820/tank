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
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
	}
}
