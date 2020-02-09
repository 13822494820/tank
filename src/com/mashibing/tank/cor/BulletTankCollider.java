package com.mashibing.tank.cor;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.GameModel;
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
				if(b.rect.intersects(t.rect)) {
					b.die();
					t.die();
					
					int eX = t.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
					int eY = t.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
					new Explode(eX, eY);
					return true;
				}
		}
		else if(o1 instanceof Tank && o2 instanceof Bullet) {
			//调用自己
			return collide(o2,o1);
		}
		return true;
	}
	
}
