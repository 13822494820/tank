package com.mashibing.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * awtû�ã���������������ѧϰ����֪ʶ
 * @author a123dsa1
 *
 */
public class T {
	public static void main(String[] args) throws Exception {
		TankFrame tf = new TankFrame();
		
		//��ʼ���з�tank
		for(int i=0;i<5;i++) {
			tf.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN,Group.BAD, tf));
		}
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();  //repaint�ȵ���update���ٵ���paint
		}
	}
}
