package ft.school21.fix_market;

import ft.school21.fix_router.server.Server;
import ft.school21.fix_utils.Decoder.AllDecoder;
import ft.school21.fix_utils.Encoder.BuyOrSellEncoder;
import ft.school21.fix_utils.Encoder.ConnectEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Market implements Runnable{

	private EventLoopGroup workerGroup;
	private final String HOST = "localhost";

	public Market() {
	}

	@Override
	public void run() {
		int port = Server.MARKET;

		workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workerGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(
									new AllDecoder(),
									new ConnectEncoder(),
									new BuyOrSellEncoder(),
									new MarketHandler());
						}
					}).option(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture future = bootstrap.connect(HOST, port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static void writeCommand(Market market) {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("MARKET_PERERJEIR");
		while (true) {
			String strCommand = null;
			try {
				strCommand = bufferedReader.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (strCommand.equals("exit")) {
				market.workerGroup.shutdownGracefully();
				break;
			}
		}
	}

}
