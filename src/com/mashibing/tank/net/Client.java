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
		// 线程池
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
			System.out.println("已经退出");
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
			//写走encoder，读走decoder，netty帮我们判断要不要执行
			//多个encoder需要顺序
			.addLast(new MsgEncoder())
			.addLast(new MsgDecoder())
			.addLast(new ClientHandler());
	}

}

//SimpleChannelInboundHandler简单的处理，泛型指定，只处理一种消息就可以使用，子类也可以用
//多种消息就不能用SimpleChannelInboundHandler
//处理的消息不一定是TankJoinMsg，可以是子弹等
//所以做一个继承，传入父类消息，判断具体类型再做具体处理
class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {

	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
		msg.handle();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// channle 第一次连上可用，写出一个字符串 Direct Memory
//		ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
//		ctx.writeAndFlush(buf);
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}

}

