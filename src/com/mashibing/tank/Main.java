package com.mashibing.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * awt没用，但用来做载体做学习其他知识
 * @author a123dsa1
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		TankFrame tf = new TankFrame();
			
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		while(true) {
			Thread.sleep(25);
			tf.repaint();  //repaint先调用update，再调用paint
		}
	}
}
