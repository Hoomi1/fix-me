package ft.school21.fix_utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class ConnectEncoder extends MessageToByteEncoder<ConnectDone> {
    @Override
    protected void encode(ChannelHandlerContext chc, ConnectDone connectDone, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(connectDone.getTypeLength());
        byteBuf.writeCharSequence(connectDone.getMessageType(), Charset.forName("UTF-8"));
//        if ()
        System.out.println("ENCODER CONNECT");
        byteBuf.writeInt(connectDone.getId());
        byteBuf.writeInt(connectDone.getChecksumLength());
    }
}
