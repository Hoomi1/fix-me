package ft.school21.fix_utils.Messages;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class FIXProtocol {

    private int messageTypeLength;
    private String messageType;
    private long marketId;
    private String checksum;
    private int checksumLength;



    public FIXProtocol(long marketId, String messageType) {
        this.marketId = marketId;
        this.messageType = messageType;
        this.messageTypeLength = messageType.length();
    }

    public FIXProtocol() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
        this.messageTypeLength = messageType.length();
    }

    public int getTypeLength() {
        return messageTypeLength;
    }

    public void setTypeLength(int messageTypeLength) {
        this.messageTypeLength = messageTypeLength;
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

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
        this.checksumLength = checksum.length();
    }


    public String HashObjectMD5(String id)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(id.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
