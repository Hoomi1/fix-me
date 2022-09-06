package ft.school21.fix_router.server;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private Date date = new Date(System.currentTimeMillis());

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
            ConnectDone connectDone = (ConnectDone) msg;
            connectDone.setId(parsNewId());
            connectDone.tagCheckSum();
            ctx.writeAndFlush(connectDone);
            hashMap.put(connectDone.getId(), ctx);
        }
        else if (fixMsg.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                fixMsg.getMessageType().equals(Message.SELL_MESSAGE.toString()))
        {
            BuyOrSell buyOrSell = (BuyOrSell) msg;

            if (buyOrSell.getMessageAction().equals(ActionMessages.EXECUTE_MESSAGE.toString()) ||
                    buyOrSell.getMessageAction().equals(ActionMessages.REJECT_MESSAGE.toString()))
            {
                hashMap.get(buyOrSell.getId()).writeAndFlush(buyOrSell);
                System.out.print("Market -> ");
                transactionFix(buyOrSell, 1);
                return;
            }
            if (getHashMap().containsKey((int) buyOrSell.getMarketId()))
            {
                System.out.println("[" + simpleDateFormat.format(date) + "] [ROUTER] [INFO] Sending a request to the market");
                getHashMap().get((int) buyOrSell.getMarketId()).channel().writeAndFlush(buyOrSell);
                System.out.print("Broker -> ");
                transactionFix(buyOrSell, 0);
            }
            else {
                System.out.println("[" + simpleDateFormat.format(date) + "] [ROUTER] [INFO] Error market id");
                System.out.print("Broker -> ");
                transactionFix(buyOrSell, 0);
                buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
                buyOrSell.tagCheckSum();
                ctx.writeAndFlush(buyOrSell);
            }
        }
    }

    private void transactionFix(BuyOrSell buyOrSell, int port) {
//        String headers = null;
//        if (buyOrSell.getMessageAction().equals(ActionMessages.NON.toString()))
        String headers = "I=" + buyOrSell.getInstrument() + "|A=" + buyOrSell.getQuantity() + "|M=Crypto|P=" + buyOrSell.getPrice() + "|";
//        else {
//            char[] t = new char[buyOrSell.getText().length()];
//            for (int i = 0; i < t.length; i++) {
//                if (buyOrSell.getText().charAt(i) == '\0')
//                    break;
//                t[i] = buyOrSell.getText().charAt(i);
//            }
//            String text = String.valueOf(t);
//            headers = "I=" + buyOrSell.getInstrument() + "|A=" + buyOrSell.getQuantity() + "|M=Crypto|P=" + buyOrSell.getPrice() + "|58=" + text + "|";
//        }
        int tagTen = (headers.length() % 256);
        String tagTenStr = String.valueOf(tagTen).length() < 3 ? "0" + String.valueOf(tagTen) : String.valueOf(tagTen);
        String protocol = "|109=" + ((port == 0) ? buyOrSell.getId() : buyOrSell.getMarketId()) + "|9=" + headers.length() + "|" + headers + "10=" + tagTenStr + "|";
        System.out.print(FIXProtocol.ANSI_PURPLE);
        System.out.println(protocol);
        System.out.print(FIXProtocol.ANSI_RESET);
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
