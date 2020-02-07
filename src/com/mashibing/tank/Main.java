package com.mashibing.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * awtû�ã���������������ѧϰ����֪ʶ
 * @author a123dsa1
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		TankFrame tf = new TankFrame();
		
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		
		//��ʼ���з�tank
		for(int i=0;i<initTankCount;i++) {
			tf.tanks.add(tf.gf.createTank(50 + i*80, 200, Dir.DOWN,Group.BAD, tf));
		}
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		while(true) {
			Thread.sleep(25);
			tf.repaint();  //repaint�ȵ���update���ٵ���paint
		}
	}
}
