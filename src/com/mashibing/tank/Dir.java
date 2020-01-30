package com.mashibing.tank;

/**
 * 在方向枚举中不需要处理stop的状态，stop应该单独处理
 * 在实际玩游戏时，子弹时根据方向打的
 * 如果是stop，子弹就打不出了
 * @author a123dsa1
 *
 */
public enum Dir {
	LEFT,UP,RIGHT,DOWN;
}
