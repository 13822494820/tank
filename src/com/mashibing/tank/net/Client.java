package com.mashibing.tank.net;




import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;
import io.netty.util.ReferenceCountUtil;

public class Client {

	public static final Client INSTANCE = new Client();
	private Channel channel = null;

	public void connect() {
		// �̳߳�
		EventLoopGroup group = new NioEventLoopGroup(1);

		Bootstrap b = new Bootstrap();

		try {
			ChannelFuture f = b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer())
					.connect("localhost", 8888);

			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						System.out.println("not connected!");
					} else {
						System.out.println("connected!");
						// initialize the channel
						channel = future.channel();
					}
				}
			});

			f.sync();
			// wait until close
			f.channel().closeFuture().sync();
			System.out.println("�Ѿ��˳�");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void send(Msg msg) {
//		ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
//		channel.writeAndFlush(buf);
		channel.writeAndFlush(msg);
	}

	public void closeConnect() {
		//this.send("_bye_");
		//channel.close();
	}
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
			//д��encoder������decoder��netty�������ж�Ҫ��Ҫִ��
			//���encoder��Ҫ˳��
			.addLast(new MsgEncoder())
			.addLast(new MsgDecoder())
			.addLast(new ClientHandler());
	}

}

//SimpleChannelInboundHandler�򵥵Ĵ�������ָ����ֻ����һ����Ϣ�Ϳ���ʹ�ã�����Ҳ������
//������Ϣ�Ͳ�����SimpleChannelInboundHandler
//�������Ϣ��һ����TankJoinMsg���������ӵ���
//������һ���̳У����븸����Ϣ���жϾ��������������崦��
class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {

	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
		msg.handle();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// channle ��һ�����Ͽ��ã�д��һ���ַ��� Direct Memory
//		ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
//		ctx.writeAndFlush(buf);
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}

}

