package ft.school21.fix_market;

import java.util.ArrayList;
import java.util.List;

public class CryptoMarket {

    private final List<Crypto> cryptoList = new ArrayList<>();
    public CryptoMarket() {
        cryptoList.add(new Crypto("Bitcoin", "\t\tBTC"));
        cryptoList.add(new Crypto("Ethereum", "\t\tETH"));
        cryptoList.add(new Crypto("Tether", "\t\tUSDT"));
        cryptoList.add(new Crypto("USD_Coin", "\t\tUSDC"));
        cryptoList.add(new Crypto("BNB", "\t\t\tBNB"));
        cryptoList.add(new Crypto("Binance_USD", "\tBUSD"));
        cryptoList.add(new Crypto("Solana", "\t\tSOL"));
        cryptoList.add(new Crypto("Dogecoin", "\t\tDOGE"));
        cryptoList.add(new Crypto("Polkadot", "\t\tDOT"));
        cryptoList.add(new Crypto("Polygon", "\t\tMATIC"));


    }

    public List<Crypto> getCryptoList() {
        return cryptoList;
    }

    @Override
    public String toString() {
        for (int i = 0; i < cryptoList.size(); ++i) {
            System.out.println((i + 1) + ") " + cryptoList.get(i).getName() + cryptoList.get(i).getCode_name());
        }
        return "\n";
    }
}
