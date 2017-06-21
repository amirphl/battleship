package ir.aut.test.tools;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.ConnectionModeFrame;
import ir.aut.test.view.ExpectationJFrame;
import ir.aut.test.view.Frame;
import ir.aut.test.view.ReceivedConnectionsFrame;

import static ir.aut.test.view.Constants.CLIENT;
import static ir.aut.test.view.Constants.SERVER;

/**
 * Created by Yana on 21/06/2017.
 */
public class Manager implements ManagerInterface {
    private ConnectionModeFrame connectionModeFrame;
    private String playerName;
    private ReceivedConnectionsFrame receivedConnectionsFrame;
    private ExpectationJFrame expectationJFrame;
    private MessageManager serverMessageManager;
    private MessageManager clientMessageManager;
    private Frame frame;

    public Manager() {
        connectionModeFrame = new ConnectionModeFrame(this);
//        waitForClient(5236, "gholam");
//        connectToServer("", 25, "ff");
    }

    @Override
    public void connectToServer(String ip, int port, String playerName) {
        this.playerName = playerName;
        expectationJFrame = new ExpectationJFrame(this);
        clientMessageManager = new MessageManager(ip, port);
        clientMessageManager.setExpectationJFrame(expectationJFrame);
    }

    @Override
    public void waitForClient(int port, String playerName) {
        this.playerName = playerName;
        serverMessageManager = new MessageManager(port);
        receivedConnectionsFrame = new ReceivedConnectionsFrame(this, serverMessageManager);
    }

    @Override
    public void startGameS() {
        frame = new Frame(serverMessageManager, SERVER, playerName);
    }

    @Override
    public void startGameC() {
        frame = new Frame(clientMessageManager, CLIENT, playerName);
    }
}
