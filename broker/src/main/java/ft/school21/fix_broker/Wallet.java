package ft.school21.fix_broker;

import ft.school21.fix_market.Cryptocurr.Crypto;
import ft.school21.fix_market.Cryptocurr.CryptoMarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wallet {

    private int money;
    private List<Crypto> walletCrypto = new ArrayList<>();

    public Wallet(int money) {
        this.money = money;
        RandCrypt();
    }

    private void RandCrypt()
    {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            walletCrypto.add(CryptoMarket.getCryptoMarket().getCryptoList().get(random.nextInt(9) + 1));
        }
    }



    //1) крипта
    //2) деньги

}
