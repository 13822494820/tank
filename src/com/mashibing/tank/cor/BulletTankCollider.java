package com.mashibing.tank.cor;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

//子弹坦克的碰撞
//也是策略模式
public class BulletTankCollider implements Collider {

	@Override
	public boolean collide(GameObject o1, GameObject o2) {
		if(o1 instanceof Bullet && o2 instanceof Tank) {
				Bullet b = (Bullet)o1;
				Tank t = (Tank)o2;
				//TODO copy code method collideWith
				if(b.collideWith(t))
					return false;
		}
		else if(o1 instanceof Tank && o2 instanceof Bullet) {
			//调用自己
			return collide(o2,o1);
		}
		return true;
	}
	
}
