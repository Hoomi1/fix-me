package ft.school21.fix_utils.Encoder;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BuyOrSellEncoder extends MessageToByteEncoder<BuyOrSell> {

    @Override
    protected void encode(ChannelHandlerContext chc, BuyOrSell buyOrSell, ByteBuf byteBuf) throws Exception {
        System.out.println("ENCODER BUY");
        byteBuf.writeInt(buyOrSell.getTypeLength());
        byteBuf.writeCharSequence(buyOrSell.getMessageType(), Charset.forName("UTF-8"));
        if (buyOrSell.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                buyOrSell.getMessageType().equals(Message.SELL_MESSAGE.toString()))
        {
            byteBuf.writeCharSequence(buyOrSell.getInstrument(), StandardCharsets.UTF_8);
            byteBuf.writeCharSequence(buyOrSell.getMessageAction(), StandardCharsets.UTF_8);
            byteBuf.writeCharSequence(buyOrSell.getChecksum(), StandardCharsets.UTF_8);
            byteBuf.writeInt(buyOrSell.getId());
            byteBuf.writeInt((int) buyOrSell.getMarketId());
            byteBuf.writeInt(buyOrSell.getActionLength());
            byteBuf.writeInt(buyOrSell.getInstrumentLength());
            byteBuf.writeInt(buyOrSell.getChecksumLength());
            byteBuf.writeInt((int) buyOrSell.getPrice());
            byteBuf.writeInt(buyOrSell.getQuantity());
        }

    }
}
