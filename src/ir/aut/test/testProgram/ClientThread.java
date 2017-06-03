package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;

/**
 * Created by Yana on 03/06/2017.
 */
public class ClientThread extends Thread {
    private MessageManager messageManager;
    private int port;

    public ClientThread(int port) {
        this.port = port;
    }

    public void run() {
        messageManager = new MessageManager("127.0.0.1", port);
    }

    public void sendMessage(String u, String p) {
        messageManager.sendRequestLogin(u, p);
    }

    public void closeConnection() {
        messageManager.onSocketClosed();
    }
}
