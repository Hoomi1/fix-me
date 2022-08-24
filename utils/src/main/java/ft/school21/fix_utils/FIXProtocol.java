package ft.school21.fix_utils;

import java.text.SimpleDateFormat;

public class FIXProtocol {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy-HH:mm:ss");
    private long messageId;
    private String messageType;


    public FIXProtocol(long messageId, String messageType) {
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
