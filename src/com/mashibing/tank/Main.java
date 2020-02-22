package com.mashibing.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.mashibing.tank.net.Client;

/**
 * awt没用，但用来做载体做学习其他知识
 * @author a123dsa1
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);
		
//		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
//		
//		//初始化敌方tank
//		for(int i=0;i<initTankCount;i++) {
//			tf.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN,Group.BAD, tf));
//		}
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
//		while(true) {
//			Thread.sleep(25);
//			tf.repaint();  //repaint先调用update，再调用paint
//		}
		new Thread(()-> {
			while(true) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tf.repaint();
			}
		}).start();
		
		Client.INSTANCE.connect();
		
		
	}
}
