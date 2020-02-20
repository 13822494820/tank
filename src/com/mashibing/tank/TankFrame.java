package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//�̳�Frame
public class TankFrame extends Frame{
	GameModel gm = GameModel.getInstance();
	
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
	
	
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
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
	
	/**
	 * ˫��������˸
	 * ��˸����Ϊ����ʾ��ͼʱ������ͼ��û�����꣬
	 * �в���ͼ��������ʾ����Ļ����
	 * ��������ڴ涨��ͬ����С�Ŀռ䣬�Ȼ����ڴ棬������һ���Ի�����Ļ��
	 */
	Image offScreenImage = null;   //����һ��ͼƬ�����ڴ�
	@Override
	public void update(Graphics g) {  //g����Ļ�Ļ���
		if(offScreenImage == null)
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		Graphics gOffScreen = offScreenImage.getGraphics();   //��ȡ����
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);   //�ػ�����
		gOffScreen.setColor(c);
		paint(gOffScreen);  //���ݵ�ǰ���ʣ������ڴ��
		g.drawImage(offScreenImage,0,0,null);   //ͼƬһ���Ի�����Ļ
	}
	
	//ÿ�λ��ƻ���պ��ٻ�����С������ʾҲ���ٻ���
	@Override
	public void paint(Graphics g) {	
		gm.paint(g);
	
	}
	
	//����һ���������̵��࣬ʹ��ͨ������ʹ�����ƶ�
	//һ���۲��ߣ���Ϊ����۲��������¼�Դ����
	//���Բ���Ҫ���¼�Դ���������������֮��
	//��Ҫ�����¼�Դ������ˢ�µȲ�������Ҫ�¼�Դ
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
			case KeyEvent.VK_S:
				gm.save();
				break;
			case KeyEvent.VK_L:
				gm.load();
				break;	

			}
			
			setMainTankDir();
			new Thread(()->new Audio("audio/tank_move.wav").play()).start();
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
			case KeyEvent.VK_CONTROL:
				gm.getMainTank().fire();
				//gm.getMainTank().handleFireKey();
				break;
			default:break;
				
			}
			
			setMainTankDir();
		}

		private void setMainTankDir() {
			if(!bL && !bU &&!bR && !bD)
				gm.getMainTank().setMoving(false);
			else
				gm.getMainTank().setMoving(true);
			if(bL) gm.getMainTank().setDir(Dir.LEFT);
			if(bU) gm.getMainTank().setDir(Dir.UP);
			if(bR) gm.getMainTank().setDir(Dir.RIGHT);
			if(bD) gm.getMainTank().setDir(Dir.DOWN);
		}
		
	}
}
