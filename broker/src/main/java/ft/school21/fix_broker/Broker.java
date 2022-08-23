package ft.school21.fix_broker;

import ft.school21.fix_router.server.Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class Broker implements Runnable{

    private String clientName;
    private EventLoopGroup workerGroup;


    public Broker(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void run() {
        int port = Server.BROKER;
        workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new BrokerHandler());
                    }
                });

    }
}
