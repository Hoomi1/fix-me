package ft.school21.fix_utils;

public class ConnectDone extends FIXProtocol{

	private int id;
	public ConnectDone(long messageId, String messageType) {
		super(messageId, messageType);
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

	}
}
