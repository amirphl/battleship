package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;

/**
 * Created by Yana on 03/06/2017.
 */
public class ServerThread extends Thread {
    private MessageManager messageManager;
    private int port;

    public ServerThread(int port) {
        this.port = port;
        messageManager = new MessageManager(port);
    }

    public void run() {
        messageManager.acceptRequest(0);
    }

    public void sentMessage(String u, String p) {
        messageManager.sendRequestLogin(u, p);
    }
}
