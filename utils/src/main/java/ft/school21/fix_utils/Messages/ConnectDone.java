package ft.school21.fix_utils.Messages;

public class ConnectDone extends FIXProtocol{

	private int id;

	public ConnectDone(int id, long messageId, String messageType) {
		super(messageId, messageType);
		this.id = id;
		tagCheckSum();
	}

	public ConnectDone() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void tagCheckSum()
	{
		String str = HashObjectMD5(String.valueOf(getId()));
		setChecksum(str);
	}

	@Override
	public String toString() {
		return "MessageAcceptConnection {" + "\n" +
				"ID = " + id + "\n" +
				"MSG_TYPE = '" + getMessageType() + "'" + "\n" +
				"CHECKSUM = '" + getChecksum() + "'" + "\n" +
				'}';
	}


}
