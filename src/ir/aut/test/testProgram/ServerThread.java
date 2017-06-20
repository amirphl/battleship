package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.Frame;

/**
 * Created by Yana on 03/06/2017.
 */
public class ServerThread extends Thread {
    private MessageManager messageManager;
    private int port;
    private ServerFrameThread serverFrameThread;
    private String myName;

    public ServerThread(int port, String myName) {
        this.port = port;
        this.myName = myName;
        messageManager = new MessageManager(port);
        serverFrameThread = new ServerFrameThread(messageManager, myName);
    }

    public void run() {
        messageManager.acceptRequest(0);
        serverFrameThread.start();
    }

    public void sentMessage(String u, String p) {
        messageManager.sendRequestLogin(u, p);
    }
}
