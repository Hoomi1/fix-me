package ft.school21.fix_broker;

import ft.school21.fix_utils.Cryptocurr.CryptoMarket;
import ft.school21.fix_utils.Database.DBTransaction;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.ActionMessages;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BrokerHandler extends ChannelInboundHandlerAdapter {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private Date date = new Date(System.currentTimeMillis());
    private static DBTransaction dbTransaction = new DBTransaction();
    private int id;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("Broker is connecting ...");
        ConnectDone connectDone = new ConnectDone(0, 0, Message.ACCEPT_MESSAGE.toString());

        ctx.writeAndFlush(connectDone);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        FIXProtocol protocol = (FIXProtocol) msg;

        if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            ConnectDone connectDone = (ConnectDone) msg;
            id = connectDone.getId();
            String headers = "|109=" + id + "|M=Crypto|";
            int tagTen = (headers.length() % 256);
            String tagTenStr = String.valueOf(tagTen).length() < 3 ? "0" + String.valueOf(tagTen) : String.valueOf(tagTen);
            System.out.println("Router -> " + FIXProtocol.ANSI_PURPLE + headers + "10=" + tagTenStr + "|" + FIXProtocol.ANSI_RESET);
            System.out.println("BROKER id = " + id);
        } else if (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
            BuyOrSell buyOrSell = (BuyOrSell) msg;
            if (buyOrSell.getMessageAction().equals(ActionMessages.EXECUTE_MESSAGE.toString()) ||
                    buyOrSell.getMessageAction().equals(ActionMessages.REJECT_MESSAGE.toString())) {

                int i = (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString())) ? 2 : 1;
                String action = (buyOrSell.getMessageAction().equals(ActionMessages.EXECUTE_MESSAGE.toString())) ? "EXECUTED" : "REJECT";
                transactionFix(buyOrSell, i, action);

                return;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        double choiceBS = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BuyOrSell buyOrSell = null;
        int ibos = Integer.parseInt(ChoiceBuyOrSell(bufferedReader));
        String command = ChoiceCript(bufferedReader);
        int numCrypt = Integer.parseInt(command);
        int choiceAm = (int) Double.parseDouble(ChoiceAmount(numCrypt, bufferedReader));
        if (ibos == 1)
            choiceBS = Double.parseDouble(ChoiceBuy(numCrypt, bufferedReader));
        else if (ibos == 2)
            choiceBS = Double.parseDouble(ChoiceSell(numCrypt, bufferedReader));
        int marketId = ChoiceMarketId(bufferedReader);
        if (ibos == 1) // Buy
        {
            buyOrSell = new BuyOrSell(marketId, Message.BUY_MESSAGE.toString(), id, ActionMessages.NON.toString(),
                    CryptoMarket.getCryptoList().get(numCrypt).getCode_name().replaceAll("\t", ""),
                    choiceAm, choiceBS);
        } else if (ibos == 2) // Sell
        {
            buyOrSell = new BuyOrSell(marketId, Message.SELL_MESSAGE.toString(), id, ActionMessages.NON.toString(),
                    CryptoMarket.getCryptoList().get(numCrypt).getCode_name().replaceAll("\t", ""),
                    choiceAm, choiceBS);
        }
        if (buyOrSell != null)
            buyOrSell.tagCheckSum();
        ctx.writeAndFlush(buyOrSell);
    }

    private void transactionFix(BuyOrSell buyOrSell, int i, String action) {
        String str = "[" + simpleDateFormat.format(date) + "] " + "[BROKER] [INFO] " + "['" + buyOrSell.getId() +
                "','Crypto','" + buyOrSell.getInstrument() + "'," + buyOrSell.getQuantity() + "," +
                buyOrSell.getPrice() + "," + ((i == 1) ? "'BUY','" : "'SELL','") + action + "']";

        String headers = "I=" + buyOrSell.getInstrument() + "|A=" + buyOrSell.getQuantity() + "|M=Crypto|P=" + buyOrSell.getPrice() + "|";
        int tagTen = (headers.length() % 256);
        String tagTenStr = String.valueOf(tagTen).length() < 3 ? "0" + String.valueOf(tagTen) : String.valueOf(tagTen);
        String protocol = "|109=" + buyOrSell.getId() + "|9=" + headers.length() + "|" + headers + "10=" + tagTenStr + "|";
        System.out.print(FIXProtocol.ANSI_PURPLE);
        System.out.println(protocol);
        System.out.print(FIXProtocol.ANSI_RESET);
        System.out.println(str);
        String trans = "|" + buyOrSell.getId() +
                "|Crypto|" + buyOrSell.getInstrument() + "|" + buyOrSell.getQuantity() + "|" +
                buyOrSell.getPrice() + ((i == 1) ? "|BUY|" : "|SELL|") + action + "|";
        dbTransaction.saveTransaction(trans);
    }

    private String ChoiceSell(int numCrypt, BufferedReader bufferedReader) throws Exception {

        String command = null;
        while (true) {
            System.out.println("How much do you want to sell?");
            System.out.print("Enter price: ");
            String input = bufferedReader.readLine();
            double iInp = 0;
            double minSell = CryptoMarket.getCryptoList().get(numCrypt).getMinSellPrice();
            try {
                iInp = Double.parseDouble(input);
                if (iInp >= minSell && iInp <= 1000) {
                    command = String.valueOf(iInp);
                    break;
                } else {
                    System.out.println("invalid input: min price " + minSell + ", max price 1000");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input: min price " + minSell + ", max price 1000");
                continue;
            }
        }
        return command;
    }

    private int ChoiceMarketId(BufferedReader bufferedReader) throws Exception {

        int marketId;
        while (true) {
            System.out.print("Enter market id: ");
            String input = bufferedReader.readLine();
            marketId = 0;
            try {
                marketId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }
        }
        return marketId;
    }

    private String ChoiceBuy(int numCrypt, BufferedReader bufferedReader) throws Exception {

        String command = null;
        while (true) {
            System.out.println("How much do you want to buy crypto?");
            System.out.print("Enter price: ");
            String input = bufferedReader.readLine();
            double iInp = 0;
            double minBuy = CryptoMarket.getCryptoList().get(numCrypt).getMinBuyPrice();
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

        String command = null;
        double amountCrypt = CryptoMarket.getCryptoList().get(numCrupt).getAmountCrypt();

        while (true) {
            System.out.println("How much cryptocurrency do you want to buy?");
            System.out.print("Enter quantity: ");
            double iInp = 0;

           String input = bufferedReader.readLine();
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

    private String ChoiceBuyOrSell(BufferedReader bufferedReader) throws Exception {

        String command = null;
        System.out.println("Press to the enter");
        String input = bufferedReader.readLine();
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

    private String ChoiceCript(BufferedReader bufferedReader) throws Exception {

        String command = null;
        CryptoMarket cryptoMarket = new CryptoMarket();
        while (true) {
            int iInp = 0;
            //TODO:
            System.out.println(cryptoMarket);
            try {
                String input = bufferedReader.readLine();
                iInp = Integer.parseInt(input);
                if (iInp >= 0 && iInp <= 9) {
                    command = String.valueOf(iInp);
                    break;
                } else {
                    System.out.println("invalid input: 0 - 9");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input: 0 - 9");
                continue;
            }
        }
        return command;
    }
}
