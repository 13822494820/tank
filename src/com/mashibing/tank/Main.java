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
			
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		while(true) {
			Thread.sleep(25);
			tf.repaint();  //repaint�ȵ���update���ٵ���paint
		}
	}
}
