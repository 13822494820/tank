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
	public static void main(String[] args) {
		//������
		Frame f = new Frame();
		f.setSize(800, 600);
		f.setResizable(false);  //�Ƿ���Ըı��С
		f.setTitle("tank war");
		f.setVisible(true);
		
		//��Ӽ����������ڼ�����closing�����
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
}
