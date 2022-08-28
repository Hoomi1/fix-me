package ft.school21.fix_router.server;

import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
        ctx.writeAndFlush(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf byteBuffer = (ByteBuf) msg;

        System.out.println((char) byteBuffer.readByte());
        FIXProtocol fixMsg = (FIXProtocol) msg;

//        if (fixMsg.getMessageId() == 0)
//            fixMsg.setMessageId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        System.out.println("QWEQE" + "\r\n" + fixMsg.getMessageType());
        if (fixMsg.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            ConnectDone connectDone = (ConnectDone) msg;
            connectDone.setId( portServer + 2);
            System.out.println(connectDone.getId());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("EXCEPTION 2");
        System.out.println(cause.getMessage());
    }
}
