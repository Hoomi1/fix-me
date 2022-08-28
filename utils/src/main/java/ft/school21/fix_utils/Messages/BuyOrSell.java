package ft.school21.fix_utils.Messages;

public class BuyOrSell extends FIXProtocol{

    private int actionLength;
    private String messageAction;
    private int id;
    private int instrumentLength;
    private String instrument;
    private int quantity;
    private int price;

    public BuyOrSell(long marketId, String messageType) {
        super(marketId, messageType);
    }

    public BuyOrSell() {
    }

    public int getActionLength() {
        return actionLength;
    }

    public void setActionLength(int actionLength) {
        this.actionLength = actionLength;
    }

    public String getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(String messageAction) {
        this.messageAction = messageAction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstrumentLength() {
        return instrumentLength;
    }

    public void setInstrumentLength(int instrumentLength) {
        this.instrumentLength = instrumentLength;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
