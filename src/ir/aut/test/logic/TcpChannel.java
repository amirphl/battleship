package ir.aut.test.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Created by Yana on 03/06/2017.
 */
public class TcpChannel {
    private Socket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public TcpChannel(SocketAddress socketAddress, int timeout) {
        try {
            System.out.println("Client :client trying to join.");
            mSocket = new Socket();
            mSocket.connect(socketAddress, timeout);
            System.out.println("Client :client connected to server.");
            getStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TcpChannel(Socket socket, int timeout) {
        mSocket = socket;
        try {
            mSocket.setSoTimeout(timeout);
            getStreams();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void getStreams() {
        try {
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Try to read specific count from input stream.
     */
    public byte[] read(final int count) {
        byte[] a = new byte[count];
        try {
            mInputStream.read(a);
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return a;
    }

    /**
     * Write bytes on output stream.
     */
    public void write(byte[] data) {
        try {
            mOutputStream.write(data);
            mOutputStream.flush();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Check socket’s connectivity.
     */
    public boolean isConnected() {
        return mSocket.isConnected();
    }

    /**
     * Try to close socket and input-output streams.
     */
    public void closeChannel() {
        try {
            mOutputStream.close();
            mInputStream.close();
            System.out.println("Connections closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
