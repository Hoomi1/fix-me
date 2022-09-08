package ft.school21.fix_market;


import ft.school21.fix_utils.Database.DBInstrum;

public class Main {
	public static void main(String[] args) {

		Market market = new Market();
		Thread threadMarket = new Thread(market);
		DBInstrum dbInstrum = new DBInstrum();
		dbInstrum.findBy();
//		Thread bd = new Thread(dbInstrum);
//		bd.start();
//		DBInstrum.findBy();
		threadMarket.start();

		try {
			threadMarket.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Market.writeCommand(market);

	}
}
