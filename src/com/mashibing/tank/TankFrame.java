package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//�̳�Frame
public class TankFrame extends Frame{
	int x=200,y=200;
	
	public TankFrame() {
		setSize(800, 600);
		setResizable(false);  //�Ƿ���Ըı��С
		setTitle("tank war");
		setVisible(true);
		
		//��Ӽ����������ڼ�����closing�����
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
	
	//ÿ�λ��ƻ���պ��ٻ�����С������ʾҲ���ٻ���
	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
		g.fillRect(x, y, 50, 50);
		x+=10;
		y+=10;
	}
}
