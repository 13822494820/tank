package com.mashibing.tank.net;

import java.util.List;
import java.util.UUID;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//8代表消息头,消息头一定要有才处理
		if(in.readableBytes() < 8) return; //TCP 拆包 粘包的问题
		
		//标记读的位置
		in.markReaderIndex();
	
		MsgType msgType = MsgType.values()[in.readInt()];
		int length = in.readInt();
		
//		readableBytes剩下可读的部分
		if(in.readableBytes() < length) {
			//指针回到标志位
			in.resetReaderIndex();
			return;
		}
		
		byte[] bytes = new byte[length];
		in.readBytes(bytes);
		
		//可以用反射
		switch (msgType) {
		case TankJoin:
			TankJoinMsg msg = new TankJoinMsg();
			msg.parse(bytes);
			break;
		default:
			break;
		}
	}

}