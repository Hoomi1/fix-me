package ft.school21.fix_utils.Messages;

public class BuyOrSell extends FIXProtocol{

    private int id;
    private String messageAction;
    private int actionLength;
    private String instrument;
    private int instrumentLength;
    private int quantity;
    private double price;
//    private String text = "-";

    public BuyOrSell(long marketId, String messageType, int id, String messageAction,  String instrument, int quantity, double price) {
        super(marketId, messageType);
        this.id = id;
        this.messageAction = messageAction;
        this.actionLength = messageAction.length();
        this.instrument = instrument;
        this.instrumentLength = instrument.length();
        this.quantity = quantity;
        this.price = price;
        tagCheckSum();
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
        this.actionLength = messageAction.length();
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
        this.instrumentLength = instrument.length();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void tagCheckSum()
    {
        String str = HashObjectMD5(String.valueOf(getId()));
        setChecksum(str);
    }

//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }

    @Override
    public String toString() {
        return "BuyOrSell{" +
                "id=" + id +
                ", messageAction='" + messageAction + '\'' +
                ", actionLength=" + actionLength +
                ", instrument='" + instrument + '\'' +
                ", instrumentLength=" + instrumentLength +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
