package ft.school21.fix_utils.Encoder;

import ft.school21.fix_utils.Messages.BuyOrSell;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class BuyOrSellEncoder extends MessageToByteEncoder<BuyOrSell> {

    @Override
    protected void encode(ChannelHandlerContext chc, BuyOrSell buyOrSell, ByteBuf byteBuf) throws Exception {
        System.out.println("ENCODER BUY");
        byteBuf.writeInt(buyOrSell.getTypeLength());
        byteBuf.writeCharSequence(buyOrSell.getMessageType(), Charset.forName("UTF-8"));
//        if ()


    }
}
