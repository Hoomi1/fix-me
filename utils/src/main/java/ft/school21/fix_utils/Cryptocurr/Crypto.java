package ft.school21.fix_utils.Cryptocurr;

import java.util.concurrent.ThreadLocalRandom;

public class Crypto {

    private String name;
    private String code_name;
    private int amountCrypt;
    private double minBuyPrice;
    private double minSellPrice;

    public Crypto(String name, String code_name, int amountCrypt, double minBuyPrice, double minSellPrice) {

        this.name = name;
        this.code_name = code_name;
        this.amountCrypt = amountCrypt;
        this.minBuyPrice = minBuyPrice;
        this.minSellPrice = minSellPrice;
//        this.amountCrypt = ThreadLocalRandom.current().nextInt(0, 1000);
//        this.minBuyPrice = ThreadLocalRandom.current().nextInt(0, 1000);
//        this.minSellPrice = ThreadLocalRandom.current().nextInt(0, 1000);
    }

    public Crypto() {
    }

    public String getName() {
        return name;
    }

    public String getCode_name() {
        return code_name;
    }

    public int getAmountCrypt() {
        return amountCrypt;
    }

    public double getMinBuyPrice() {
        return minBuyPrice;
    }

    public double getMinSellPrice() {
        return minSellPrice;
    }
}
