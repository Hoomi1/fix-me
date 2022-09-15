package ft.school21.fix_utils.Cryptocurr;

import java.util.ArrayList;
import java.util.List;

public class CryptoMarket {

    private static CryptoMarket cryptoMarket;
    private static final List<Crypto> cryptoList = new ArrayList<>();
    public CryptoMarket() {
//        cryptoList.add(new Crypto("Bitcoin", "\t\tBTC"));
//        cryptoList.add(new Crypto("Ethereum", "\t\tETH"));
//        cryptoList.add(new Crypto("Tether", "\t\t\tUSDT"));
//        cryptoList.add(new Crypto("USD_Coin", "\t\tUSDC"));
//        cryptoList.add(new Crypto("BNB", "\t\t\tBNB"));
//        cryptoList.add(new Crypto("Binance_USD", "\tBUSD"));
//        cryptoList.add(new Crypto("Solana", "\t\t\tSOL"));
//        cryptoList.add(new Crypto("Dogecoin", "\t\tDOGE"));
//        cryptoList.add(new Crypto("Polkadot", "\t\tDOT"));
//        cryptoList.add(new Crypto("Polygon", "\t\tMATIC"));

    }

//    public static CryptoMarket getCryptoMarket()
//    {
//        if (cryptoMarket == null)
//            cryptoMarket = new CryptoMarket();
//        return cryptoMarket;
//    }

    public static void addCrypto(Crypto crypto)
    {
        cryptoList.add(crypto);
    }


    public static List<Crypto> getCryptoList() {
        return cryptoList;
    }

    @Override
    public String toString() {
        for (int i = 0; i < cryptoList.size(); ++i) {
            System.out.println(i + ") " + cryptoList.get(i).getName() + (cryptoList.get(i).getCode_name().equals("BNB") ? "\t\t" : "\t") + cryptoList.get(i).getCode_name());
        }
        return "";
    }
}
