package ft.school21.fix_broker;

import ft.school21.fix_utils.ConnectDone;
import ft.school21.fix_utils.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BrokerHandler extends ChannelInboundHandlerAdapter {


    public BrokerHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connect broker");
        ConnectDone connectDone = new ConnectDone(0, Message.ACCEPT_MESSAGE.toString());
        super.channelActive(ctx);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
    }
}
