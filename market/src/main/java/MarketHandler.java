import ft.school21.fix_market.Crypto;
import ft.school21.fix_market.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class MarketHandler extends ChannelInboundHandlerAdapter {

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

		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		super.channelReadComplete(ctx);
	}
}
