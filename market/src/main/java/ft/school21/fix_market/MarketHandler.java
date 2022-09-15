package ft.school21.fix_market;

import ft.school21.fix_utils.Cryptocurr.Crypto;
import ft.school21.fix_utils.Cryptocurr.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MarketHandler extends ChannelInboundHandlerAdapter {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    private int id;
    private Random random = new Random();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);

        System.out.println("Market is connecting ...");

        List<Crypto> cryptos = CryptoMarket.getCryptoList();
        System.out.println("POINT 1");
        System.out.println("\n\t\tname " + "\t\t\tcode " + "\t\tamount" + "\tmin_buy " + "min_sell");
        for (int i = 0; i < cryptos.size(); i++) {
            System.out.println("[MARKET] [INFO] " + cryptos.get(i).getName() + (cryptos.get(i).getCode_name().equals("BNB") ? "   \t\t" : "   \t")
                    + "\t" + cryptos.get(i).getCode_name() + (cryptos.get(i).getCode_name().equals("MATIC") ? "   " : "   \t") + "\t" + cryptos.get(i).getAmountCrypt() + " \t" +
                    cryptos.get(i).getMinBuyPrice() + " | " + cryptos.get(i).getMinSellPrice());
        }
        System.out.println("POINT 2");
        ConnectDone connectDone = new ConnectDone(0, 0, Message.ACCEPT_MESSAGE.toString());
        ctx.writeAndFlush(connectDone);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);

        FIXProtocol protocol = (FIXProtocol) msg;
        if (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
            BuyOrSell buyOrSell = (BuyOrSell) msg;
            if (buyOrSell.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
                forBuy(ctx, buyOrSell);
            } else if (buyOrSell.getMessageType().equals(Message.SELL_MESSAGE.toString())) {
                forSell(ctx, buyOrSell);
            }

        } else if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            ConnectDone connectDone = (ConnectDone) msg;
            id = connectDone.getId();
            String headers = "|109=" + id + "|M=Crypto|";
            int tagTen = (headers.length() % 256);
            String tagTenStr = String.valueOf(tagTen).length() < 3 ? "0" + String.valueOf(tagTen) : String.valueOf(tagTen);
            System.out.println("Router -> " + FIXProtocol.ANSI_PURPLE + headers + "10=" + tagTenStr + "|" + FIXProtocol.ANSI_RESET);
            System.out.println("MARKET id = " + id);
        }
    }

	private void forBuy(ChannelHandlerContext ctx, BuyOrSell buyOrSell) {

        String text = null;
        if (random.nextBoolean()) {
            buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
//            buyOrSell.setText("Request denied :( -> REJECT\0");
            text = "Request denied :( -> REJECT";
            System.out.println("[" + simpleDateFormat.format(date) + "] [MARKET] [INFO] " + text);
        }
		else
        {
            buyOrSell.setMessageAction(ActionMessages.EXECUTE_MESSAGE.toString());
//            buyOrSell.setText("Thanks you for buying :) -> EXECUTE\0");
            text = "Thanks you for buying :) -> EXECUTE";
            System.out.println("[" + simpleDateFormat.format(date) + "] [MARKET] [INFO] " + text);
        }
        transactionFix(buyOrSell, text);
		buyOrSell.tagCheckSum();
		ctx.writeAndFlush(buyOrSell);
	}

    private void transactionFix(BuyOrSell buyOrSell, String text) {
//        String text = null;
//        text = buyOrSell.getText();
        String headers = "I=" + buyOrSell.getInstrument() + "|A=" + buyOrSell.getQuantity() + "|M=Crypto|P=" + buyOrSell.getPrice() + "|58="
                + text + "|";
        int tagTen = (headers.length() % 256);
        String tagTenStr = String.valueOf(tagTen).length() < 3 ? "0" + String.valueOf(tagTen) : String.valueOf(tagTen);
        String protocol = "|109=" + buyOrSell.getMarketId() + "|9=" + headers.length() + "|" + headers + "10=" + tagTenStr + "|";
        System.out.print(FIXProtocol.ANSI_PURPLE);
        System.out.println(protocol);
        System.out.print(FIXProtocol.ANSI_RESET);
    }

	private void forSell(ChannelHandlerContext ctx, BuyOrSell buyOrSell) {
        String text = null;
        if (random.nextBoolean()) {
            buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
//            buyOrSell.setText("Request denied :( -> REJECT\0");
            text = "Request denied :( -> REJECT";
            System.out.println("[" + simpleDateFormat.format(date) + "] [MARKET] [INFO] " + text);
        }
        else {
//            buyOrSell.setText("Thanks you for selling :) -> EXECUTE\0");
            text = "Thanks you for selling :) -> EXECUTE";
            buyOrSell.setMessageAction(ActionMessages.EXECUTE_MESSAGE.toString());
            System.out.println("[" + simpleDateFormat.format(date) + "] [MARKET] [INFO] " + text);
        }
        transactionFix(buyOrSell, text);
		buyOrSell.tagCheckSum();
		ctx.writeAndFlush(buyOrSell);
	}

}
