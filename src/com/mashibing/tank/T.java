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
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
	}
}
