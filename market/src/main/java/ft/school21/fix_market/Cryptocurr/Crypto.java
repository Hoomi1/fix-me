package ft.school21.fix_market.Cryptocurr;

import java.util.concurrent.ThreadLocalRandom;

public class Crypto {

    private String name;
    private String code_name;
    private int amountCrypt;
    private double minBuyPrice;
    private double minSellPrice;

    public Crypto(String name, String code_name) {

        this.name = name;
        this.code_name = code_name;
        this.amountCrypt = ThreadLocalRandom.current().nextInt(0, 1000);
        this.minBuyPrice = ThreadLocalRandom.current().nextInt(0, 1000);
        this.minSellPrice = ThreadLocalRandom.current().nextInt(0, 1000);
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
