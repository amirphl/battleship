package ir.aut.test.logic;

import ir.aut.test.logic.messages.*;
import ir.aut.test.view.first.EInterface;
import ir.aut.test.view.first.RCInterface;
import ir.aut.test.view.second.UI1;
import ir.aut.test.view.second.UI2;
import ir.aut.test.view.second.UI3;

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
    private UI1 frame;
    private UI2 shipsJPanel;
    private UI3 chatJFrame;
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

    public void sendReadinessCondition(boolean bool) {
        mNetworkHandlerList.get(index).sendMessage(new ReadinessMessage(bool));
    }

    public void sendLocation(int i, int j, int condition) {
        mNetworkHandlerList.get(index).sendMessage(new LocationMessage((byte) i, (byte) j, (byte) condition));
    }

    public void sendAccept() {
        mNetworkHandlerList.get(index).sendMessage(new AcceptMessage());
    }

    public void sendMyName(String username) {
        mNetworkHandlerList.get(index).sendMessage(new NameMessage(username));
    }

    public void sendTerminate() {
        mNetworkHandlerList.get(index).sendMessage(new TerminateMessage());
    }

    public void sendConversation(String text) {
        mNetworkHandlerList.get(index).sendMessage(new ConversationMessage(text));
    }

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
        shipsJPanel.setOpponentReadiness(message.getReadinessCondition());
    }

    private void consumeLocation(LocationMessage message) {
        if (message.getmCondition() == 3)
            frame.destroyMyShips(message.getmI(), message.getmJ());
        else
            frame.destroyOpponentShips(message.getmI(), message.getmJ(), message.getmCondition());
    }

    private void consumeAccept(AcceptMessage message) {
        expectationJFrame.close();
    }

    private void consumeOpponentName(NameMessage message) {
        frame.setOpponentName(message.getUsername());
    }

    private void consumeTerminate(TerminateMessage message) {
        frame.loose();
    }

    private void consumeConversation(ConversationMessage message) {
        chatJFrame.addText(message.getText(), 2);
    }

    public void setShipsJPanel(UI2 shipsJPanel) {
        this.shipsJPanel = shipsJPanel;
    }

    public void setChatJFrame(UI3 chatJFrame) {
        this.chatJFrame = chatJFrame;
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
                break;
            case MessageTypes.REQUEST_NAME:
                consumeOpponentName((NameMessage) baseMessage);
                break;
            case MessageTypes.TERMINATE:
                consumeTerminate((TerminateMessage) baseMessage);
                break;
            case MessageTypes.CONVERSATION:
                consumeConversation((ConversationMessage) baseMessage);
                break;
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

