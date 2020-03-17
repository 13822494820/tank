package com.mashibing.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

public class TankJoinMsg extends Msg{
	public int x, y;
	public Dir dir;
	public boolean moving;
	public Group group;
	//作为tank的标识,128位，一台机唯一的，应该是服务器生成最合适,但很复杂一点
	public UUID id;
	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.group = group;
		this.id = id;
	}
	
	public TankJoinMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.dir= t.getDir();
		this.group = t.getGroup();
		this.id = t.getId();
		this.moving = t.isMoving();
	}
	
	
	public TankJoinMsg() {
		
	}
	
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			//TODO:先读TYPE信息，根据TYPE信息处理不同的消息
			//略过消息类型
			//dis.readInt();
			
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.moving = dis.readBoolean();
			this.group = Group.values()[dis.readInt()];
			this.id = new UUID(dis.readLong(), dis.readLong());
			//this.name = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null; 
		byte[] bytes = null;
		try {
			//jdk自带，如果用netty的bytebuf也可以，不过信息就绑定在netty上了
			//如果以后不用netty，就要重写，
			//但jdk自带的方法即使不用netty，也可以用
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			//dos.writeInt(TYPE.ordinal());
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeBoolean(moving);  //1个字节，网络最小一个字节
			//ordinal获取下标值的DOWN的3，不要写字面值，
			dos.writeInt(group.ordinal());
			//id的高64位，当作long类型
			dos.writeLong(id.getMostSignificantBits());
			//id的低64位
			dos.writeLong(id.getLeastSignificantBits());
			//dos.writeUTF(name);
			dos.flush();
			//内存的字节数组转为保存的字节数组bytes
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(dos != null) {
					dos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bytes;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName())
			   .append("[")
			   .append("uuid=" + id + " | ")
			   //.append("name=" + name + " | ")
			   .append("x=" + x + " | ")
			   .append("y=" + y + " | ")
			   .append("moving=" + moving + " | ")
			   .append("dir=" + dir + " | ")
			   .append("group=" + group + " | ")
			   .append("]");
		return builder.toString();
	}
	
	@Override
	public void handle() {
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||
				TankFrame.INSTANCE.findByUUID(this.id) != null) return;
		System.out.println(this);
		Tank t = new Tank(this);
		TankFrame.INSTANCE.addTank(t);
		
		//send a new TankJoinMsg to the new joined tank
		Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}

	
}