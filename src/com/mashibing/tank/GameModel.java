package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

	Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	
	public GameModel() {
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

		//��ʼ���з�tank
		for(int i=0;i<initTankCount;i++) {
			tanks.add(new Tank(50 + i*80, 200, Dir.DOWN,Group.BAD, this));
		}
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("�ӵ�������" + bullets.size(), 10, 60);
		g.drawString("���˵�����" + tanks.size(), 10, 80);
		g.drawString("��ը������" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		
		//list���������ɾ���������java.util.ConcurrentModificationException
		//��Ϊ�ڲ�״̬��һ��
//		for(Bullet b: bullets) {
//			b.paint(g);
//		}
		for(int i=0;i<bullets.size();i++) {
			bullets.get(i).paint(g);
//			if(bullets.get(i).getGroup()==Group.GOOD)
//				System.out.println(bullets.get(i).rect);
		}
		
		
		//System.out.println(myTank.rect);
		for(int i=0;i<tanks.size();i++) {
			tanks.get(i).paint(g);
		}
		
		for(int i=0;i<explodes.size();i++) {
			explodes.get(i).paint(g);
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			for(int j=0;j<tanks.size();j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
		//������
//		for(Iterator<Bullet> it =bullets.iterator();it.hasNext();) {
//			Bullet b = it.next();
//			if(!b.live) 
//				it.remove();
//		}
		
	}

	public Tank getMainTank() {
		return myTank;
	}
	

}
