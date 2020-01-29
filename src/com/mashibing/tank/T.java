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
	public static void main(String[] args) {
		//窗体类
		Frame f = new Frame();
		f.setSize(800, 600);
		f.setResizable(false);  //是否可以改变大小
		f.setTitle("tank war");
		f.setVisible(true);
		
		//添加监听器，现在监听着closing这件事
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
}
