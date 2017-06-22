package ir.aut.test.testProgram;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.second.Frame;

import static ir.aut.test.view.Constants.CLIENT;

/**
 * Created by Yana on 14/06/2017.
 */
public class ClientThreadFrame extends Thread {
    private MessageManager messageManager;
    private Frame frame;
    private String myName;

    public ClientThreadFrame(MessageManager messageManager, String myName) {
        this.messageManager = messageManager;
        this.myName = myName;
    }

    public void run() {
        frame = new Frame(messageManager, CLIENT, myName);
    }
}
