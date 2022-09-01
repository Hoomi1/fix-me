package ft.school21.fix_router.server;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private int portServer;
    private static HashMap<Integer, ChannelHandlerContext> hashMap  = new HashMap<>();

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
        FIXProtocol fixMsg = (FIXProtocol) msg;
        if (fixMsg.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            System.out.println("ServerHANDLER_MESSAGE");
            ConnectDone connectDone = (ConnectDone) msg;
            connectDone.setId(parsNewId());
            connectDone.tagCheckSum();
            ctx.writeAndFlush(connectDone);
            hashMap.put(connectDone.getId(), ctx);
        }
        else if (fixMsg.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                fixMsg.getMessageType().equals(Message.SELL_MESSAGE.toString()))
        {
            System.out.println("ServerHANDLER_ButSell");
            BuyOrSell buyOrSell = (BuyOrSell) msg;
            if (getHashMap().containsKey((int) buyOrSell.getMarketId()))
            {
                System.out.println("Sending a request to the market");
                getHashMap().get((int) buyOrSell.getMarketId()).channel().writeAndFlush(buyOrSell);
            }
            else {
                System.err.println("Error market id");
                buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
                buyOrSell.tagCheckSum();
                ctx.writeAndFlush(buyOrSell);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("EXCEPTION 2");
    }

    public int parsNewId() {
        long lID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        String str = String.valueOf(lID);
        String str1 = str.substring(0, 4);
        String strId = null;
        if (portServer == Server.MARKET)
            strId = str1.concat("01");
        else
            strId = str1.concat("00");
        return Integer.parseInt(strId);
    }

    public HashMap<Integer, ChannelHandlerContext> getHashMap() {
        return hashMap;
    }
}
