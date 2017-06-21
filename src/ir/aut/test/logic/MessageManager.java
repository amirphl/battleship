package ir.aut.test.logic;

import ir.aut.test.tools.ManagerInterface;
import ir.aut.test.view.EInterface;
import ir.aut.test.view.RCInterface;
import ir.aut.test.view.UI1;
import ir.aut.test.view.UI2;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yana on 03/06/2017.
 * sendRequestLogin
 * consumeMessage
 * onMessageReceived
 * onSocketClosed
 */
public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback,
        NetworkHandler.INetworkHandlerCallback {
    private ServerSocketHandler mServerSocketHandler;
    private List<NetworkHandler> mNetworkHandlerList;
    private int index = 0;
    private UI2 shipsJPanel;
    private UI1 frame;
    private RCInterface receivedConnectionsFrame;
    private EInterface expectationJFrame;

    /**
     * Instantiate server socket handler and start it. (Call this constructor in host mode)
     */
    public MessageManager(int port) {
        mNetworkHandlerList = new ArrayList<>();
        mServerSocketHandler = new ServerSocketHandler(port, this, this);
        mServerSocketHandler.start();
    }

    /**
     * Instantiate a network handler and start it. (Call this constructor in guest mode)
     */
    public MessageManager(String ip, int port) {
        mNetworkHandlerList = new ArrayList<>();
        NetworkHandler networkHandler = new NetworkHandler(new InetSocketAddress(ip, port), this);
        mNetworkHandlerList.add(networkHandler);
        networkHandler.start();
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * Create a RequestLoginMessage object and sent it through the appropriate network handler.
     */
    public void sendRequestLogin(String username, String password) {
        mNetworkHandlerList.get(index).sendMessage(new RequestLoginMessage(username, password));
    }

    public void sendReadinessCondition(boolean bool, String username) {
        mNetworkHandlerList.get(index).sendMessage(new ReadinessMessage(bool, username));
    }

    public void sendLocation(int i, int j, int condition) {
        mNetworkHandlerList.get(index).sendMessage(new LocationMessage((byte) i, (byte) j, (byte) condition));
    }

    public void sendAccept() {
        mNetworkHandlerList.get(index).sendMessage(new AcceptMessage());
    }

//    public void sendRequestName(String username) {
//        mNetworkHandlerList.get(index).sendMessage(new RequestOpponentName(username));
//    }

    /**
     * Accepts which netWorkHandler to connect and communicate.
     */
    public void acceptRequest(int i) {
        index = i;
        try {
            mNetworkHandlerList.get(i).start();
            sendAccept();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            index = 0;
            mNetworkHandlerList.get(0).start();
        }
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * Use the message.
     */
    private void consumeRequestLogin(RequestLoginMessage message) {
        System.out.println(message.getUsername() + " " + message.getPassword());
    }

    private void consumeReadiness(ReadinessMessage message) {
        shipsJPanel.setOpponentReady(message.getReadinessCondition());
        shipsJPanel.setOpponentName(message.getmUsername());
    }

    private void consumeLocation(LocationMessage message) {
        if (message.getmCondition() == 3)
            frame.impartMySquares(message.getmI(), message.getmJ());
        else
            frame.impartOpponentSquares(message.getmI(), message.getmJ(), message.getmCondition());
    }

    private void consumeAccept(AcceptMessage message) {
        expectationJFrame.close();
    }

    public void setShipsJPanel(UI2 shipsJPanel) {
        this.shipsJPanel = shipsJPanel;
    }

    public void setFrame(UI1 frame) {
        this.frame = frame;
    }

    public void setReceivedConnectionsFrame(RCInterface receivedConnectionsFrame) {
        this.receivedConnectionsFrame = receivedConnectionsFrame;
    }

    public void setExpectationJFrame(EInterface expectationJFrame) {
        this.expectationJFrame = expectationJFrame;
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * According to the message type of baseMessage, call corresponding method to use it.
     */
    @Override
    public void onMessageReceived(BaseMessage baseMessage) {
        switch (baseMessage.getMessageType()) {
            case MessageTypes.REQUEST_LOGIN:
                consumeRequestLogin((RequestLoginMessage) baseMessage);
                break;
            case MessageTypes.READINESS:
                consumeReadiness((ReadinessMessage) baseMessage);
                break;
            case MessageTypes.LOCATION:
                consumeLocation((LocationMessage) baseMessage);
                break;
            case MessageTypes.ACCEPT:
                consumeAccept((AcceptMessage) baseMessage);
        }
    }

    @Override
    public void onSocketClosed() {
        mNetworkHandlerList.get(index).stopSelf();
    }

    /**
     * Add network handler to the list.
     */
    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
        mNetworkHandlerList.add(networkHandler);
        new Thread() {
            public void run() {
                receivedConnectionsFrame.addJPanel("", "");
            }
        }.start();
        System.out.println("networkHandler added to list");
    }
}

