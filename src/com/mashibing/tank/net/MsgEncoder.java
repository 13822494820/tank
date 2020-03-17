package com.mashibing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * �ֽڿ��Կ�����
 * ������л���c++��server�˴�����
 * @author a123dsa1
 *
 */
public class MsgEncoder extends MessageToByteEncoder<Msg>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
		//�ȷ�msg����
		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		//�ٷ�����
		buf.writeInt(bytes.length);
		//�������
		buf.writeBytes(bytes);
	}
	

}