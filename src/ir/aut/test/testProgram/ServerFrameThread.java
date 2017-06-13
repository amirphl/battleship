package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.Frame;

import static ir.aut.test.view.Constants.SERVER;

/**
 * Created by Yana on 06/06/2017.
 */
public class ServerFrameThread extends Thread {
    private MessageManager messageManager;
    private Frame frame;

    public ServerFrameThread(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void run() {
        frame = new Frame(messageManager, SERVER);
    }
}
