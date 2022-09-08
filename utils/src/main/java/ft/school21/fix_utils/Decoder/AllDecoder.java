package ft.school21.fix_utils.Decoder;

import ft.school21.fix_utils.Cryptocurr.Crypto;
import ft.school21.fix_utils.Cryptocurr.CryptoMarket;
import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AllDecoder extends ReplayingDecoder<Object> {

    Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        FIXProtocol protocol = new FIXProtocol();
        protocol.setMessageType(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());

        if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {

            ConnectDone connectDone = new ConnectDone();
//            for (int i = 0; i < 10; i++) {
////                Crypto crypto = new Crypto(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString(),
////                        byteBuf.readCharSequence(byteBuf.readInt(), charset).toString(),
////                        byteBuf.readInt(),
////                        byteBuf.readInt(),
////                        byteBuf.readInt());
//
////                CryptoMarket.addCrypto(crypto);
//                System.out.println(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());
//                System.out.println(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());
//                System.out.println(byteBuf.readInt());
//            }
            connectDone.setMessageType(protocol.getMessageType());
            connectDone.setId(byteBuf.readInt());
            connectDone.setChecksum(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());
            list.add(connectDone);
        } else if (protocol.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.SELL_MESSAGE.toString())) {
            BuyOrSell buyOrSell = new BuyOrSell();
            buyOrSell.setMessageType(protocol.getMessageType());
            buyOrSell.setMessageAction(byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString());
            buyOrSell.setId(byteBuf.readInt());
            buyOrSell.setInstrument(byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString());
            buyOrSell.setMarketId(byteBuf.readInt());
            buyOrSell.setPrice(byteBuf.readInt());
            buyOrSell.setQuantity(byteBuf.readInt());
//            buyOrSell.setText(byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString());
            buyOrSell.tagCheckSum();
            list.add(buyOrSell);
        }
    }
}
