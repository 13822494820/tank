package com.mashibing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 字节可以跨语言
 * 如果序列化，c++的server端处理不了
 * @author a123dsa1
 *
 */
public class MsgEncoder extends MessageToByteEncoder<Msg>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
		//先发msg类型
		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		//再发长度
		buf.writeInt(bytes.length);
		//最后发数据
		buf.writeBytes(bytes);
	}
	

}