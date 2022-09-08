package ft.school21.fix_utils.Encoder;

import ft.school21.fix_utils.Cryptocurr.Crypto;
import ft.school21.fix_utils.Cryptocurr.CryptoMarket;
import ft.school21.fix_utils.Messages.ConnectDone;
import ft.school21.fix_utils.MessagesEnum.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConnectEncoder extends MessageToByteEncoder<ConnectDone> {
    @Override
    protected void encode(ChannelHandlerContext chc, ConnectDone connectDone, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(connectDone.getTypeLength());
        byteBuf.writeCharSequence(connectDone.getMessageType(), StandardCharsets.UTF_8);

        if (connectDone.getMessageType().equals(Message.ACCEPT_MESSAGE.toString())) {

//            CryptoMarket cryptoMarket = new CryptoMarket();
//            List<Crypto> cryptos = new ArrayList<>();
//            cryptos = cryptoMarket.getCryptoList();
//            for (int i = 0; i < cryptos.size(); i++) {
//                byteBuf.writeCharSequence(cryptos.get(i).getName(), StandardCharsets.UTF_8);
//                byteBuf.writeCharSequence(cryptos.get(i).getCode_name(), StandardCharsets.UTF_8);
//                byteBuf.writeInt(cryptos.get(i).getAmountCrypt());
//                byteBuf.writeInt((int) cryptos.get(i).getMinBuyPrice());
//                byteBuf.writeInt((int) cryptos.get(i).getMinSellPrice());
//            }
            byteBuf.writeInt(connectDone.getId());
            byteBuf.writeInt(connectDone.getChecksumLength());
            byteBuf.writeCharSequence(connectDone.getChecksum(), StandardCharsets.UTF_8);
        }
    }
}
