package ir.aut.test.logic;

import ir.aut.test.head.Connector;
import ir.aut.test.logic.messages.*;
import ir.aut.test.view.first.WaitingJFrameCallBack;
import ir.aut.test.view.first.RCFCallBack;
import ir.aut.test.view.second.FrameCallBack;
import ir.aut.test.view.second.ShipsJPanelCallBack;
import ir.aut.test.view.second.ChatBoxCallBack;

import javax.swing.*;
import java.io.IOException;
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
    private Connector connector;

//    private FrameCallBack iFrameCallBack;
//    private ShipsJPanelCallBack shipsJPanelCallBack;
//    private ChatBoxCallBack chatBoxCallBack;
//    private RCFCallBack rcfCallBack;
//    private WaitingJFrameCallBack waitingJFrameCallBack;

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
    public MessageManager(String ip, int port) throws IOException {
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

    public void sendReadinessCondition(boolean b) {
        mNetworkHandlerList.get(index).sendMessage(new ReadinessMessage(b));
    }

    public void sendLocation(int i, int j, int condition) {
        mNetworkHandlerList.get(index).sendMessage(new LocationMessage((byte) i, (byte) j, (byte) condition));
    }

    public void sendAccept() {
        mNetworkHandlerList.get(index).sendMessage(new AcceptMessage());
    }

    public void sendReject() {
        mNetworkHandlerList.get(index).sendMessage(new RejectMessage());
    }

    public void sendMyName(String username) {
        mNetworkHandlerList.get(index).sendMessage(new NameMessage(username));
    }

    public void sendTerminate(int i) {
        mNetworkHandlerList.get(index).sendMessage(new TerminateMessage(i));
    }

    public void sendConversation(String text) {
        mNetworkHandlerList.get(index).sendMessage(new ConversationMessage(text));
    }

    public void sendIP(String username, String ip) {
        mNetworkHandlerList.get(index).sendMessage(new IPMessage(username, ip));
    }

    public void sendRequestLeave(String ip) {
        mNetworkHandlerList.get(index).sendMessage(new RequestLeaveMessage(ip));
    }

    /**
     * Accepts which netWorkHandler to connect and communicate.
     */
    public void acceptRequest(int i) {
        NetworkHandler handler = mNetworkHandlerList.get(i);
        mNetworkHandlerList.remove(i);
        for (int j = 0; j < mNetworkHandlerList.size(); j++)
            rejectRequest(j);
        mNetworkHandlerList.clear();
        mNetworkHandlerList.add(handler);
        index = 0;
        sendAccept();
    }

    public void rejectRequest(int i) {
        index = i;
        sendReject();
    }

    /**
     * IMPORTANT: Request login is an example message and doesn’t relate to this project!
     * Use the message.
     */
    private void consumeRequestLogin(RequestLoginMessage message) {
        System.out.println(message.getUsername() + " " + message.getPassword());
    }

    private void consumeReadiness(ReadinessMessage message) {
        connector.setReadinessCondition(message.getReadinessCondition());
        connector.sendMessage("ConsumeReadinessCondition");
//        shipsJPanelCallBack.setOpponentReadiness(message.getReadinessCondition());
    }

    private void consumeLocation(LocationMessage message) {
        connector.setCondition(message.getmCondition());
        connector.setmX(message.getmI());
        connector.setmY(message.getmJ());
        connector.sendMessage("ConsumeLocationMessage");
//        if (message.getmCondition() == 3)
//            iFrameCallBack.destroyMyShips(message.getmI(), message.getmJ());
//        else
//            iFrameCallBack.destroyOpponentShips(message.getmI(), message.getmJ(), message.getmCondition());
    }

    private void consumeAccept(AcceptMessage message) {
        connector.sendMessage("ConsumeAcceptMessage");
//        waitingJFrameCallBack.close(1);
    }

    private void consumeReject(RejectMessage message) {
        connector.sendMessage("ConsumeRejectMessage");
//        waitingJFrameCallBack.close(0);
    }

    private void consumeOpponentName(NameMessage message) {
        connector.setOpponentName(message.getUsername());
        connector.sendMessage("ConsumeOpponentName");
//        iFrameCallBack.setOpponentName();
    }

    private void consumeTerminate(TerminateMessage message) {
        connector.setTerminateCondition(message.getI());
        connector.sendMessage("ConsumeTerminateMessage");
//        if (message.getI() == 1)
//            iFrameCallBack.loose();
//        else
//            iFrameCallBack.win(message.getI());
    }

    private void consumeConversation(ConversationMessage message) {
        connector.setConversationMessage(message.getText());
        connector.sendMessage("ConsumeConversationMessage");
//        chatBoxCallBack.addText(message.getText(), 2);
    }

    private void consumeIP(IPMessage message) {
        connector.setIp(message.getIp());
        connector.setOpponentName(message.getUsername());
        connector.sendMessage("AddSubJPanel");
//        rcfCallBack.addIPJPanel(message.getUsername(), message.getIp());
    }

    private void consumeRequestLeave(RequestLeaveMessage message) {
        connector.setIp(message.getIp());
        connector.sendMessage("DeleteSubJPanelByIP");
//        rcfCallBack.deleteByIP(message.getIp());
    }

//    public void setiFrameCallBack(FrameCallBack iFrameCallBack) {
//        this.iFrameCallBack = iFrameCallBack;
//    }
//
//    public void setShipsJPanelCallBack(ShipsJPanelCallBack shipsJPanelCallBack) {
//        this.shipsJPanelCallBack = shipsJPanelCallBack;
//    }
//
//    public void setChatBoxCallBack(ChatBoxCallBack chatBoxCallBack) {
//        this.chatBoxCallBack = chatBoxCallBack;
//    }
//
//    public void setRcfCallBack(RCFCallBack rcfCallBack) {
//        this.rcfCallBack = rcfCallBack;
//    }
//
//    public void setIWaitingJFrameCallBack(WaitingJFrameCallBack WaitingJFrameCallBack) {
//        this.waitingJFrameCallBack = WaitingJFrameCallBack;
//    }

    public void setConnector(Connector c) {
        connector = c;
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
            case MessageTypes.REJECT:
                consumeReject((RejectMessage) baseMessage);
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
            case MessageTypes.IP:
                consumeIP((IPMessage) baseMessage);
                break;
            case MessageTypes.REQUEST_LEAVE:
                consumeRequestLeave((RequestLeaveMessage) baseMessage);
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
        System.out.println("networkHandler added to list");
    }
}

