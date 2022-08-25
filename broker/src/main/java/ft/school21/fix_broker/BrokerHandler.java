package ft.school21.fix_broker;

import ft.school21.fix_utils.BuyOrSell;
import ft.school21.fix_utils.ConnectDone;
import ft.school21.fix_utils.FIXProtocol;
import ft.school21.fix_utils.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BrokerHandler extends ChannelInboundHandlerAdapter {


    public BrokerHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Broker is connecting");
        ConnectDone connectDone = new ConnectDone(0, Message.ACCEPT_MESSAGE.toString());
//        super.channelActive(ctx);
        System.out.println(ctx.channel().remoteAddress().toString().substring(11));
        ctx.writeAndFlush(connectDone);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        FIXProtocol protocol = (FIXProtocol) msg;

        if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString()))
        {
            ConnectDone connectDone = (ConnectDone) msg;

        }
        else if (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.BUY_MESSAGE.toString()))
        {
            BuyOrSell buyOrSell = (BuyOrSell) msg;

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
    }
}
