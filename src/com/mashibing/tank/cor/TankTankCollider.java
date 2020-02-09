package com.mashibing.tank.cor;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

//坦克与坦克的碰撞
//没有撞死，责任链可以继续
//也是策略模式
public class TankTankCollider implements Collider {

	@Override
	public boolean collide(GameObject o1, GameObject o2) {
		if(o1 instanceof Tank && o2 instanceof Tank) {
				Tank t1 = (Tank)o1;
				Tank t2 = (Tank)o2;
				if(t1.getRect().intersects(t2.getRect())) {
					t1.back();
					t2.back();
				}
		}
		return true;
	}
	
}
