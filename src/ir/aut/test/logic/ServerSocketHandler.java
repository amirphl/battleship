package ir.aut.test.logic;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static ir.aut.test.view.Constants.SERVER;

/**
 * Created by Yana on 03/06/2017.
 * run
 * stopSelf
 */
public class ServerSocketHandler extends Thread {
    private ServerSocket serverSocket;
    private NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback;
    private IServerSocketHandlerCallback iServerSocketHandlerCallback;
    private boolean flag;
    private Socket socket = null;
    private NetworkHandler networkHandler;

    public ServerSocketHandler(int port, NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback,
                               IServerSocketHandlerCallback iServerSocketHandlerCallback) {
        try {
            serverSocket = new ServerSocket(port, 200);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Invalid port.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        this.iNetworkHandlerCallback = iNetworkHandlerCallback;
        this.iServerSocketHandlerCallback = iServerSocketHandlerCallback;
        flag = true;
    }

    /**
     * While server socket is connected and stop is not called:
     * if a connection receives, then create a network handler and pass it through onNewConnectionReceived
     * else sleep for 100 ms.
     */
    @Override
    public void run() {
        while (flag) {
            try {
                System.out.println("Server : waiting for joining client.");
                socket = serverSocket.accept();
                System.out.println("Server : client joined.");
                if (socket != null) {
                    networkHandler = new NetworkHandler(socket, iNetworkHandlerCallback);
                    networkHandler.start();
                    iServerSocketHandlerCallback.onNewConnectionReceived(networkHandler);
                } else
                    sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Kill the thread and close the server socket.
     */
    public void stopSelf() {
        flag = false;
    }

    public interface IServerSocketHandlerCallback {
        void onNewConnectionReceived(NetworkHandler networkHandler);
    }
}
