package ft.school21.fix_broker;

import ft.school21.fix_market.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BrokerHandler extends ChannelInboundHandlerAdapter {

	private int id;

	public BrokerHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
		System.out.println("Broker is connecting ...");
		ConnectDone connectDone = new ConnectDone(0, 0, Message.ACCEPT_MESSAGE.toString());
//        System.out.println(ctx.channel().remoteAddress().toString().substring(11));
		ctx.writeAndFlush(connectDone);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
		FIXProtocol protocol = (FIXProtocol) msg;
		if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
			ConnectDone connectDone = (ConnectDone) msg;
			id = connectDone.getId();

		}
//		else if (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString()) ||
//				protocol.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
//			BuyOrSell buyOrSell = (BuyOrSell) msg;
//		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

		String command = null;

		int ibos = 0;
		int numCrypt = 0;
		int choiceAm = 0;
		double choiceBu = 0;

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BuyOrSell buyOrSell = null;
		ibos = Integer.parseInt(ChoiceBuyOrSell(command, bufferedReader));
		command = ChoiceCript(String.valueOf(ibos), bufferedReader);
		numCrypt = Integer.parseInt(command);
		choiceAm = (int) Double.parseDouble(ChoiceAmount(numCrypt, bufferedReader));
		choiceBu = Double.parseDouble(ChoiceBuy(numCrypt, bufferedReader));

		if (ibos == 1) // Buy
		{
			//TODO: id market поменять
			buyOrSell = new BuyOrSell(0, Message.BUY_MESSAGE.toString(), id, ActionMessages.NON.toString(),
					CryptoMarket.getCryptoMarket().getCryptoList().get(numCrypt).getCode_name().replaceAll("\t",""),
					choiceAm, choiceBu);
		}
		else if (ibos == 2) // Sell
		{
			//TODO: id market поменять
			buyOrSell = new BuyOrSell(0, Message.SELL_MESSAGE.toString(), id, ActionMessages.NON.toString(),
					CryptoMarket.getCryptoMarket().getCryptoList().get(numCrypt).getCode_name().replaceAll("\t",""),
					choiceAm, choiceBu);
		}
		System.out.println(buyOrSell);
	}

	private String ChoiceBuy(int numCrypt, BufferedReader bufferedReader) throws Exception{

		String input = null;
		String command = null;
		while (true) {
			System.out.println("How much do you want to buy crypto?");
			System.out.print("Enter price: ");
			input = bufferedReader.readLine();
			double iInp = 0;
			double minBuy = CryptoMarket.getCryptoMarket().getCryptoList().get(numCrypt).getMinBuyPrice();
			try {
				iInp = Double.parseDouble(input);
				if (iInp >= minBuy && iInp <= 1000) {
					command = String.valueOf(iInp);
					break;
				} else {
					System.out.println("invalid input: min price " + minBuy + ", max price 1000");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("invalid input: min price " + minBuy + ", max price 1000");
				continue;
			}
		}
		return command;
	}

	private String ChoiceAmount(int numCrupt, BufferedReader bufferedReader) throws Exception {
		String input = null;
		String command = null;
		double amountCrypt = CryptoMarket.getCryptoMarket().getCryptoList().get(numCrupt).getAmountCrypt();

		while (true) {
			System.out.println("How much cryptocurrency do you want to buy?");
			System.out.print("Enter quantity: ");
			double iInp = 0;

			input = bufferedReader.readLine();
			try {
				iInp = Double.parseDouble(input);
				if (iInp >= 0 && iInp <= amountCrypt) {
					command = String.valueOf(iInp);
					break;
				} else {
					System.out.println("invalid input: maximum quantity " + amountCrypt);
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("invalid input: maximum quantity " + amountCrypt);
				continue;
			}
		}
		return command;
	}

	private String ChoiceBuyOrSell(String command, BufferedReader bufferedReader) throws Exception {

		String input = null;

		System.out.println("Press to the enter");
		input = bufferedReader.readLine();
		while (true) {
			System.out.println("1) Buy" + "\n" + "2) Sell");
			input = bufferedReader.readLine();
			if (!input.equals("1") && !input.equals("2")) {
				System.out.println("invalid input: 1 or 2");
				continue;
			} else {
				command = input;
				break;
			}
		}
		return command;
	}

	private String ChoiceCript(String command, BufferedReader bufferedReader) throws Exception {

		String input = null;
		while (true) {
			int iInp = 0;
			System.out.println(CryptoMarket.getCryptoMarket());
			try {
				input = bufferedReader.readLine();
				iInp = Integer.parseInt(input);
				if (iInp >= 1 && iInp <= 10) {
					command = String.valueOf(iInp);
					break;
				} else {
					System.out.println("invalid input: 1 - 10");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("invalid input: 1 - 10");
				continue;
			}
		}
		return command;
	}
}
