package com.mashibing.tank.cor;

import java.util.LinkedList;
import java.util.List;

import com.mashibing.tank.GameObject;

/**
 * 	可以链条和链条组合
 * 	boolean可以控制是否停止责任链
 * @author a123dsa1
 *
 */
public class ColliderChain implements Collider{
	
	//不需要随机访问（下标）,所以用链表
	private List<Collider> colliders = new LinkedList<>();
	
	public ColliderChain() {
		add(new BulletTankCollider());
		add(new TankTankCollider());
		add(new BulletWallCollider());
		add(new TankWallCollider());
	}
	
	public void add(Collider c) {
		colliders.add(c);
	}

	public boolean collide(GameObject o1, GameObject o2) {
		for (int i = 0; i < colliders.size(); i++) {
			if(!colliders.get(i).collide(o1, o2))
				return false;
		}
		return true;
	}
	
	

}
