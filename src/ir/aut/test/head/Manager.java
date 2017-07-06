package ir.aut.test.head;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.first.ConnectionModeFrame;
import ir.aut.test.view.first.WaitingJFrame;
import ir.aut.test.view.second.Frame;
import ir.aut.test.view.first.ReceivedConnectionsJFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static ir.aut.test.view.Constants.CLIENT;
import static ir.aut.test.view.Constants.SERVER;

/**
 * Created by Yana on 21/06/2017.
 */
public class Manager implements ManagerInterface {
    private ConnectionModeFrame connectionModeFrame;
    private String playerName;
    private ReceivedConnectionsJFrame receivedConnectionsJFrame;
    private WaitingJFrame waitingJFrame;
    private Connector connector;
    private MessageManager serverMessageManager;
    private MessageManager clientMessageManager;
    private Frame frame;

    public Manager() {
        connectionModeFrame = new ConnectionModeFrame(this);
    }

    @Override
    public void connectToServer(String ip, int port, String playerName) {
        this.playerName = playerName;
        clientMessageManager = new MessageManager(ip, port);
        connector = new Connector(clientMessageManager, CLIENT);
        clientMessageManager.setConnector(connector);
        waitingJFrame = new WaitingJFrame(this, connector);
        try {
            connector.setIp(InetAddress.getLocalHost().getHostAddress());
            connector.setPlayerName(playerName);
            connector.sendMessage("SendClientIpToServer");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void waitForClient(int port, String playerName) {
        this.playerName = playerName;
        serverMessageManager = new MessageManager(port);
        connector = new Connector(serverMessageManager, SERVER);
        serverMessageManager.setConnector(connector);
        receivedConnectionsJFrame = new ReceivedConnectionsJFrame(this, connector);
    }

    @Override
    public void startGameS() {
        frame = new Frame(connector, SERVER, playerName);
    }

    @Override
    public void startGameC() {
        frame = new Frame(connector, CLIENT, playerName);
    }
}
