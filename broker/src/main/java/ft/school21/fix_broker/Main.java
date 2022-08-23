package ft.school21.fix_broker;

public class Main {
    public static void main(String[] args) {

        if (args.length == 1) {
            Broker broker = new Broker("broker");
            Thread threadBroker = new Thread(broker);
            threadBroker.start();

            try {
                threadBroker.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("[java -jar broker.jar address]");
        }
    }
}
