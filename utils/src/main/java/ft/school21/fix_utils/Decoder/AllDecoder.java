package ft.school21.fix_utils.Decoder;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.Messages.FIXProtocol;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AllDecoder extends ReplayingDecoder<Object> {

    Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        FIXProtocol protocol = new FIXProtocol();
        protocol.setMessageType(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());

        if (protocol.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {
            System.out.println("MESSAGE_DECODER");
            ConnectDone connectDone = new ConnectDone();
            connectDone.setMessageType(protocol.getMessageType());
            connectDone.setId(byteBuf.readInt());
            connectDone.setChecksum(byteBuf.readCharSequence(byteBuf.readInt(), charset).toString());
            list.add(connectDone);
        } else if (protocol.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                protocol.getMessageType().equals(Message.SELL_MESSAGE.toString())) {
            System.out.println("BuySell_DECODER");
            BuyOrSell buyOrSell = new BuyOrSell();
            buyOrSell.setMessageType(protocol.getMessageType());
            buyOrSell.setMessageAction(byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString());
            buyOrSell.setId(byteBuf.readInt());
            buyOrSell.setInstrument(byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString());
            buyOrSell.setMarketId(byteBuf.readInt());
            buyOrSell.setPrice(byteBuf.readInt());
            buyOrSell.setQuantity(byteBuf.readInt());
            buyOrSell.tagCheckSum();
            list.add(buyOrSell);
        }
    }
}
