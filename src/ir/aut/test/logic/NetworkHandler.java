package ir.aut.test.logic;

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

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socketAddress, 300);
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        createQueues();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();
    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        mTcpChannel = new TcpChannel(socket, 300);
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
        return mTcpChannel.read(MAXIMUM_SIZE);
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
                    }
                } else if (mReceivedQueue.isEmpty()) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
