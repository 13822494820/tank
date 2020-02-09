package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.mashibing.tank.cor.BulletTankCollider;
import com.mashibing.tank.cor.Collider;
import com.mashibing.tank.cor.ColliderChain;
import com.mashibing.tank.cor.TankTankCollider;

public class GameModel {
	
	private static final GameModel INSTANCE = new GameModel();	

	static {
		INSTANCE.init();
	}
	
	Tank myTank ;
//	List<Bullet> bullets = new ArrayList<>();
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
	ColliderChain chain = new ColliderChain();
//	Collider collider = new BulletTankCollider();
//	Collider collider2 = new TankTankCollider();
	
	private List<GameObject> objects = new ArrayList<>();

	private GameModel() {
		
	}
	
	private void init() {
		myTank = new Tank(200,400,Dir.DOWN,Group.GOOD);
		
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

		//初始化敌方tank
		for(int i=0;i<initTankCount;i++) {
			new Tank(50 + i*100, 200, Dir.DOWN,Group.BAD);
		}
		
		//初始化墙
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 300, 50, 200));
	}
	
	public static GameModel getInstance() {
		return INSTANCE;
	}
	
	public void add(GameObject go) {
		this.objects.add(go);
	}
	
	public void remove(GameObject go) {
		this.objects.remove(this);
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
//		g.drawString("子弹的数量" + bullets.size(), 10, 60);
//		g.drawString("敌人的数量" + tanks.size(), 10, 80);
//		g.drawString("爆炸的数量" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		
		//list用这个不能删除，会出现java.util.ConcurrentModificationException
		//因为内部状态不一致
//		for(Bullet b: bullets) {
//			b.paint(g);
//		}
//		for(int i=0;i<bullets.size();i++) {
//			bullets.get(i).paint(g);
//			if(bullets.get(i).getGroup()==Group.GOOD)
//				System.out.println(bullets.get(i).rect);
//		}
		
		for(int i=0;i<objects.size();i++) {
			objects.get(i).paint(g);
		}
		
		//System.out.println(myTank.rect);
//		for(int i=0;i<tanks.size();i++) {
//			tanks.get(i).paint(g);
//		}
//		
//		for(int i=0;i<explodes.size();i++) {
//			explodes.get(i).paint(g);
//		}
		
		//碰撞
		for (int i = 0; i < objects.size(); i++) {
			for(int j=i+1;j<objects.size();j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
				//for
				chain.collide(o1,o2);
			}
		}
//		for (int i = 0; i < bullets.size(); i++) {
//			for(int j=0;j<tanks.size();j++) {
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
		
		//方法二
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
