package ft.school21.fix_utils;

import java.text.SimpleDateFormat;

public class FIXProtocol {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy-HH:mm:ss");

    private int typeLength;
    private String messageType;
    private long marketId;
    private int checksumLength;



    public FIXProtocol(long marketId, String messageType) {
    }

    public FIXProtocol() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getTypeLength() {
        return typeLength;
    }

    public void setTypeLength(int typeLength) {
        this.typeLength = typeLength;
    }

    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public int getChecksumLength() {
        return checksumLength;
    }

    public void setChecksumLength(int checksumLength) {
        this.checksumLength = checksumLength;
    }
}
