package ft.school21.fix_utils.Encoder;

import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class ConnectEncoder extends MessageToByteEncoder<ConnectDone> {
    @Override
    protected void encode(ChannelHandlerContext chc, ConnectDone connectDone, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(connectDone.getTypeLength());
        byteBuf.writeCharSequence(connectDone.getMessageType(), StandardCharsets.UTF_8);

        if (connectDone.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {

            byteBuf.writeInt(connectDone.getId());
            byteBuf.writeInt(connectDone.getChecksumLength());
            byteBuf.writeCharSequence(connectDone.getChecksum(), StandardCharsets.UTF_8);
        }
    }
}
