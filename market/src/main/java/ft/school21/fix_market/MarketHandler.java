package ft.school21.fix_market;

import ft.school21.fix_market.cryptocurr.Crypto;
import ft.school21.fix_market.cryptocurr.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.Random;

public class MarketHandler extends ChannelInboundHandlerAdapter {

    private int id;
    private Random random = new Random();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);

        System.out.println("Market is connecting ...");

        List<Crypto> cryptos = CryptoMarket.getCryptoMarket().getCryptoList();
        System.out.println("\n\t\t\t\tname " + "\t\t\tcode " + "\tamount" + "\tmin_buy " + "min_sell");
        for (int i = 0; i < cryptos.size(); i++) {
            System.out.println("[MARKET] [INFO] " + cryptos.get(i).getName() + " " + cryptos.get(i).getCode_name() + " \t" + cryptos.get(i).getAmountCrypt() + "\t\t " +
                    cryptos.get(i).getMinBuyPrice() + " | " + cryptos.get(i).getMinSellPrice());
        }
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

            if (buyOrSell.getMessageAction().equals(ActionMessages.EXECUTE_MESSAGE.toString()) ||
                    buyOrSell.getMessageAction().equals(ActionMessages.REJECT_MESSAGE.toString()))
            {
                System.out.println("Request " + buyOrSell.getMessageType());
                return;
            }

            if (buyOrSell.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
                forBuy(ctx, buyOrSell);
            } else if (buyOrSell.getMessageType().equals(Message.SELL_MESSAGE.toString())) {
                forSell(ctx, buyOrSell);
            }

        } else if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            ConnectDone connectDone = (ConnectDone) msg;
            id = connectDone.getId();
            System.out.println("MARKET id = " + id);
        }
    }

	//TODO: адекватные условия для запросов
	private void forBuy(ChannelHandlerContext ctx, BuyOrSell buyOrSell) {

        if (random.nextBoolean()) {
            buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
            System.out.println("[MARKET] [INFO] Request denied :( -> REJECT");
        }
		else
        {
            buyOrSell.setMessageAction(ActionMessages.EXECUTE_MESSAGE.toString());
            System.out.println("[MARKET] [INFO] Thanks you for buying :) -> EXECUTE");
        }
		buyOrSell.tagCheckSum();
		ctx.writeAndFlush(buyOrSell);
	}

	//TODO: адекватные условия для запросов
	private void forSell(ChannelHandlerContext ctx, BuyOrSell buyOrSell) {

        if (random.nextBoolean()) {
            buyOrSell.setMessageAction(ActionMessages.REJECT_MESSAGE.toString());
            System.out.println("[MARKET] [INFO] Request denied :( -> REJECT");
        }
        else {
            buyOrSell.setMessageAction(ActionMessages.EXECUTE_MESSAGE.toString());
            System.out.println("[MARKET] [INFO] Thanks you for selling :) -> EXECUTE");
        }
		buyOrSell.tagCheckSum();
		ctx.writeAndFlush(buyOrSell);
	}

}
