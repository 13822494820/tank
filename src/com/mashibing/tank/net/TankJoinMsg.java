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
	//��Ϊtank�ı�ʶ,128λ��һ̨��Ψһ�ģ�Ӧ���Ƿ��������������,���ܸ���һ��
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
			//TODO:�ȶ�TYPE��Ϣ������TYPE��Ϣ����ͬ����Ϣ
			//�Թ���Ϣ����
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
			//jdk�Դ��������netty��bytebufҲ���ԣ�������Ϣ�Ͱ���netty����
			//����Ժ���netty����Ҫ��д��
			//��jdk�Դ��ķ�����ʹ����netty��Ҳ������
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			//dos.writeInt(TYPE.ordinal());
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeBoolean(moving);  //1���ֽڣ�������Сһ���ֽ�
			//ordinal��ȡ�±�ֵ��DOWN��3����Ҫд����ֵ��
			dos.writeInt(group.ordinal());
			//id�ĸ�64λ������long����
			dos.writeLong(id.getMostSignificantBits());
			//id�ĵ�64λ
			dos.writeLong(id.getLeastSignificantBits());
			//dos.writeUTF(name);
			dos.flush();
			//�ڴ���ֽ�����תΪ������ֽ�����bytes
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