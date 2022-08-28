package ft.school21.fix_broker;

import ft.school21.fix_market.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static ft.school21.fix_broker.Broker.cryptoMarket;

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
        if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            ConnectDone connectDone = (ConnectDone) msg;

        } else if (protocol.getMessageType().equals(Message.SELL_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.BUY_MESSAGE.toString())) {
            BuyOrSell buyOrSell = (BuyOrSell) msg;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        String command = null;
        String input = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

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



        while (true) {
            int iInp = 0;
            System.out.println(cryptoMarket);
            input = bufferedReader.readLine();
            try {
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

        while (true)
        {
            System.out.println("Количество крипты которые хотите купить :)");
            int iInp = 0;
            input = bufferedReader.readLine();
            try {

            }
            catch (NumberFormatException e)
            {

            }
        }
//        System.out.println("Press to the enter");
//        while (true)
//        {
//            input = bufferedReader.readLine();
//            System.out.println("1) Buy" + "\n" + "2) Sell");
//            if (input.equals("1") || input.equals("2"))
//            {
//                command = input;
//                break;
//            }
//            else {
//                if (!input.equals(""))
//                    System.out.println("invalid input: 1 or 2");
//                continue;
//            }
//        }
//
//        while (true)
//        {
//            input = bufferedReader.readLine();
//            if (command.equals("1")) {
//                System.out.println("1) название крипты, которые хотите купить( 1) ETH\tEthereum)");
//            }
//            else if (command.equals("2"))
//            {
//                System.out.println("1) название крипты, которые хотите продать( 1) ETH\tEthereum)");
//            }
//        }
//
//        while (true)
//        {
//            command = bufferedReader.readLine();
//            System.out.println("Количество крипты которые хотите купить :)");
//        }
//
//        while (true)
//        {
//            command = bufferedReader.readLine();
//            System.out.println("За сколько хочешь купить каждую крипту :)");
//        }
    }

    private void CommandBuy() {

    }
}
