package ir.aut.test.logic;

import ir.aut.test.logic.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Yana on 03/06/2017.
 */
public class NetworkHandler extends Thread {
    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue;
    private Queue<byte[]> mReceivedQueue;
    private ReceivedMessageConsumer mConsumerThread;
    private INetworkHandlerCallback iNetworkHandlerCallback;
    private final int MAXIMUM_SIZE = 100;
    private boolean flag = true;

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) throws IOException {
        mTcpChannel = new TcpChannel(socketAddress, 100);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        createQueues();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();
    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socket, 100);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        createQueues();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();
    }

    private void createQueues() {
        mSendQueue = new LinkedList<>();
        mReceivedQueue = new LinkedList<>();
    }

    /**
     * Add serialized bytes of message to the sendQueue.
     */
    public void sendMessage(BaseMessage baseMessage) {
        mSendQueue.add(baseMessage.getSerialized());
    }

    /**
     * While channel is connected and stop is not called:
     * if sendQueue is not empty, then poll a message and send it
     * else if readChannel() is returning bytes, then add it to receivedQueue.
     */
    @Override
    public void run() {
        byte[] arrayOfBytes;
        while (mTcpChannel.isConnected() && !isInterrupted()) {
            if (!mSendQueue.isEmpty()) {
                mTcpChannel.write(mSendQueue.poll());
            } else if ((arrayOfBytes = readChannel()) != null) {
                mReceivedQueue.add(arrayOfBytes);
            }
        }
    }

    /**
     * Kill the thread and close the channel.
     */
    public void stopSelf() {
        interrupt();
        flag = false;
        mTcpChannel.closeChannel();
    }

    /**
     * Try to read some bytes from the channel.
     */
    private byte[] readChannel() {
        return mTcpChannel.read();
    }

    private class ReceivedMessageConsumer extends Thread {
        /**
         * While channel is connected and stop is not called:
         * if there exists message in receivedQueue, then create a message object
         * from appropriate class based on message type byte and pass it through onMessageReceived
         * else if receivedQueue is empty, then sleep 100 ms.
         */
        @Override
        public void run() {
            byte[] array;
            byte messageType;
            while (mTcpChannel.isConnected() && !isInterrupted() && flag) {
                if (!mReceivedQueue.isEmpty()) {
                    array = mReceivedQueue.poll();
                    messageType = array[5];
                    switch (messageType) {
                        case MessageTypes.REQUEST_LOGIN:
                            RequestLoginMessage requestLoginMessage = new RequestLoginMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(requestLoginMessage);
                            break;
                        case MessageTypes.READINESS:
                            ReadinessMessage readinessMessage = new ReadinessMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(readinessMessage);
                            break;
                        case MessageTypes.LOCATION:
                            LocationMessage locationMessage = new LocationMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(locationMessage);
                            break;
                        case MessageTypes.ACCEPT:
                            AcceptMessage acceptMessage = new AcceptMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(acceptMessage);
                            break;
                        case MessageTypes.REJECT:
                            RejectMessage rejectMessage = new RejectMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(rejectMessage);
                            break;
                        case MessageTypes.REQUEST_NAME:
                            NameMessage nameMessage = new NameMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(nameMessage);
                            break;
                        case MessageTypes.TERMINATE:
                            TerminateMessage terminateMessage = new TerminateMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(terminateMessage);
                            break;
                        case MessageTypes.CONVERSATION:
                            ConversationMessage conversationMessage = new ConversationMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(conversationMessage);
                            break;
                        case MessageTypes.IP:
                            IPMessage ipMessage = new IPMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(ipMessage);
                            break;
                        case MessageTypes.REQUEST_LEAVE:
                            RequestLeaveMessage requestLeaveMessage = new RequestLeaveMessage(array);
                            iNetworkHandlerCallback.onMessageReceived(requestLeaveMessage);
                            break;
                    }
                } else if (mReceivedQueue.isEmpty()) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }

    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage);

        void onSocketClosed();
    }
}
