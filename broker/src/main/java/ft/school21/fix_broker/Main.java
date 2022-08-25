package ft.school21.fix_broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

       // if (args.length == 1) {
        Broker broker = new Broker();
        Thread threadBroker = new Thread(broker);
        threadBroker.start();

        try {
            threadBroker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Broker.writeCommand(broker);
//        } else {
//            System.out.println("[java -jar broker.jar address]");
//        }
    }
}
