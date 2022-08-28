package ft.school21.fix_broker;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BrokerHandler extends ChannelInboundHandlerAdapter {


    public BrokerHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("Broker is connecting");
        ConnectDone connectDone = new ConnectDone(0, 0, Message.ACCEPT_MESSAGE.toString());
//        System.out.println(ctx.channel().remoteAddress().toString().substring(11));
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
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("COMPLETE");
    }
}
