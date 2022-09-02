package ft.school21.fix_utils.Encoder;

import ft.school21.fix_utils.Messages.BuyOrSell;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class BuyOrSellEncoder extends MessageToByteEncoder<BuyOrSell> {


    @Override
    protected void encode(ChannelHandlerContext chc, BuyOrSell buyOrSell, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(buyOrSell.getTypeLength());
        byteBuf.writeCharSequence(buyOrSell.getMessageType(), StandardCharsets.UTF_8);

        if (buyOrSell.getMessageType().equals(Message.BUY_MESSAGE.toString()) ||
                buyOrSell.getMessageType().equals(Message.SELL_MESSAGE.toString())) {

            byteBuf.writeInt(buyOrSell.getActionLength());
            byteBuf.writeCharSequence(buyOrSell.getMessageAction(), StandardCharsets.UTF_8);
            byteBuf.writeInt(buyOrSell.getId());
            byteBuf.writeInt(buyOrSell.getInstrumentLength());
            byteBuf.writeCharSequence(buyOrSell.getInstrument(), StandardCharsets.UTF_8);
            byteBuf.writeInt((int) buyOrSell.getMarketId());
            byteBuf.writeInt((int) buyOrSell.getPrice());
            byteBuf.writeInt(buyOrSell.getQuantity());
            byteBuf.writeInt(buyOrSell.getChecksumLength());
            byteBuf.writeCharSequence(buyOrSell.getChecksum(), StandardCharsets.UTF_8);

        }
    }
}
