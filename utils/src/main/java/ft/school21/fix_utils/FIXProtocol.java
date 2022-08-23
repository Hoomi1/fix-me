package ft.school21.fix_utils;

import java.text.SimpleDateFormat;

public class FIXProtocol {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy-HH:mm:ss");
    private long messageId;


    public FIXProtocol() {
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
