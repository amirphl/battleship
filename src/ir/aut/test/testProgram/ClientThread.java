package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;

/**
 * Created by Yana on 03/06/2017.
 */
public class ClientThread extends Thread {
    private MessageManager messageManager;
    private int port;
    private ClientThreadFrame clientThreadFrame;
    private String myName;

    public ClientThread(int port, String myName) {
        this.port = port;
        this.myName = myName;
    }

    public void run() {
        messageManager = new MessageManager("127.0.0.1", port);
//        messageManager = new MessageManager("192.168.1.2", port);
        clientThreadFrame = new ClientThreadFrame(messageManager, myName);
        clientThreadFrame.start();
    }

    public void sendMessage(String u, String p) {
//        messageManager.sendRequestLogin(u, p);
    }

    public void closeConnection() {
        messageManager.onSocketClosed();
    }
}
