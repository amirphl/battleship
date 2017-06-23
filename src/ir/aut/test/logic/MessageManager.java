package ir.aut.test.logic;

import ir.aut.test.logic.messages.*;
import ir.aut.test.view.first.IWaitingJFrameCallBack;
import ir.aut.test.view.first.RCFCallBack;
import ir.aut.test.view.second.IFrameCallBack;
import ir.aut.test.view.second.IShipsJPanelCallBack;
import ir.aut.test.view.second.IChatJFrameCallBack;

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
    private IFrameCallBack iFrameCallBack;
    private IShipsJPanelCallBack iShipsJPanelCallBack;
    private IChatJFrameCallBack iChatJFrameCallBack;
    private RCFCallBack rcfCallBack;
    private IWaitingJFrameCallBack iWaitingJFrameCallBack;

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

    public void sendTerminate() {
        mNetworkHandlerList.get(index).sendMessage(new TerminateMessage());
    }

    public void sendConversation(String text) {
        mNetworkHandlerList.get(index).sendMessage(new ConversationMessage(text));
    }

    public void sendIP(String username, String ip) {
        mNetworkHandlerList.get(index).sendMessage(new IPMessage(username, ip));
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
        iShipsJPanelCallBack.setOpponentReadiness(message.getReadinessCondition());
    }

    private void consumeLocation(LocationMessage message) {
        if (message.getmCondition() == 3)
            iFrameCallBack.destroyMyShips(message.getmI(), message.getmJ());
        else
            iFrameCallBack.destroyOpponentShips(message.getmI(), message.getmJ(), message.getmCondition());
    }

    private void consumeAccept(AcceptMessage message) {
        iWaitingJFrameCallBack.close(1);
    }

    private void consumeReject(RejectMessage message) {
        iWaitingJFrameCallBack.close(0);
    }

    private void consumeOpponentName(NameMessage message) {
        iFrameCallBack.setOpponentName(message.getUsername());
    }

    private void consumeTerminate(TerminateMessage message) {
        iFrameCallBack.loose();
    }

    private void consumeConversation(ConversationMessage message) {
        iChatJFrameCallBack.addText(message.getText(), 2);
    }

    private void consumeIP(IPMessage message) {
        rcfCallBack.addIPJPanel(message.getUsername(), message.getIp());
    }

    public void setiFrameCallBack(IFrameCallBack iFrameCallBack) {
        this.iFrameCallBack = iFrameCallBack;
    }

    public void setiShipsJPanelCallBack(IShipsJPanelCallBack iShipsJPanelCallBack) {
        this.iShipsJPanelCallBack = iShipsJPanelCallBack;
    }

    public void setiChatJFrameCallBack(IChatJFrameCallBack iChatJFrameCallBack) {
        this.iChatJFrameCallBack = iChatJFrameCallBack;
    }

    public void setRcfCallBack(RCFCallBack rcfCallBack) {
        this.rcfCallBack = rcfCallBack;
    }

    public void setIWaitingJFrameCallBack(IWaitingJFrameCallBack IWaitingJFrameCallBack) {
        this.iWaitingJFrameCallBack = IWaitingJFrameCallBack;
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

