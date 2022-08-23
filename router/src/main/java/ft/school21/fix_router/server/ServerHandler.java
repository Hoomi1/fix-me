package ft.school21.fix_router.server;

import ft.school21.fix_utils.FIXProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private int portServer;

    public ServerHandler(int portServer) {
        this.portServer = portServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        if (Server.MARKET == portServer)
            System.out.println("Connection done MARKET");
        else if (Server.BROKER == portServer)
            System.out.println("Connection done BROKER");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        FIXProtocol fixMsg = (FIXProtocol) msg;

        if (fixMsg.getMessageId() == 0)
            fixMsg.setMessageId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
