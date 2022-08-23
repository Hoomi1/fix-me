package ft.school21.fix_router;

import ft.school21.fix_router.server.Server;

public class Main {

    public static void main(String[] args) {

        Server marketServer = new Server(Server.MARKET);
        Server brokerServer = new Server(Server.BROKER);

        System.out.println("[ROUTER] [INFO] Application server start");
        System.out.println("[ROUTER] [INFO] Starting brokerSocketServer thread on port 5000");
        System.out.println("[ROUTER] [INFO] Starting marketSocketServer thread on port 5001");

        Thread threadBroker = new Thread(brokerServer);
        threadBroker.start();

        Thread threadMarket = new Thread(marketServer);
        threadMarket.start();

    }


}
