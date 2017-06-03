package ir.aut.test.logic;

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
public class MessageManager implements ServerSocketHandler.IServerSocketHandlerCallback, NetworkHandler.INetworkHandlerCallback {
    private ServerSocketHandler mServerSocketHandler;
    private List<NetworkHandler> mNetworkHandlerList;
    private int index = 0;

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

    /**
     * Accepts which netWorkHandler to connect and communicate.
     */
    public void acceptRequest(int i) {
        index = i;
        try {
            mNetworkHandlerList.get(i).start();
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
