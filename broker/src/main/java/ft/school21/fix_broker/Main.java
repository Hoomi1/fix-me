package ft.school21.fix_broker;

import ft.school21.fix_utils.Database.DBInstrum;

public class Main {
    public static void main(String[] args) {

        Broker broker = new Broker();
        Thread threadBroker = new Thread(broker);
        DBInstrum dbInstrum = new DBInstrum();
        Thread bd = new Thread(dbInstrum);
        bd.start();
        threadBroker.start();

        try {
            threadBroker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Broker.writeCommand(broker);
    }
}
