package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//�̳�Frame
public class TankFrame extends Frame{
	Tank myTank = new Tank(200,200,Dir.DOWN);
	Bullet b = new Bullet(300,300,Dir.DOWN);
	
	public TankFrame() {
		setSize(800, 600);
		setResizable(false);  //�Ƿ���Ըı��С
		setTitle("tank war");
		setVisible(true);
		
		this.addKeyListener(new MyKeyListener());
		
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
		myTank.paint(g);
		b.paint(g);
	}
	
	//����һ���������̵��࣬ʹ��ͨ������ʹ�����ƶ�
	class MyKeyListener extends KeyAdapter{

		//��boolean��ʾ���µļ�,��������������
		//�п���ͬʱ�������¼������ڼ����ʱ��һ���Ⱥ�˳��ģ���ʹ����ȥͬʱ
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		//��������ʱ����
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			//���ﲻ��б���ƶ�����Ϊswitchһ�θ�һ��
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}
			
			setMainTankDir();
		}

		//���ɿ�ʱ����
		@Override
		public void keyReleased(KeyEvent e) {
			//��̧��ʱ���ָ�
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;

			default:
				break;
			}
			
			setMainTankDir();
		}

		private void setMainTankDir() {
			if(!bL && !bU &&!bR && !bD)
				myTank.setMoving(false);
			else
				myTank.setMoving(true);
			if(bL) myTank.setDir(Dir.LEFT);
			if(bU) myTank.setDir(Dir.UP);
			if(bR) myTank.setDir(Dir.RIGHT);
			if(bD) myTank.setDir(Dir.DOWN);
		}
		
	}
}
