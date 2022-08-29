public class Main {
	public static void main(String[] args) {

		Market market = new Market();
		Thread threadBroker = new Thread(market);
		threadBroker.start();

		try {
			threadBroker.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Market.writeCommand(market);

	}
}
