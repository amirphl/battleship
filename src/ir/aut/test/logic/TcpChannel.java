package ir.aut.test.logic;

import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ArrayUtilsTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;


/**
 * Created by Yana on 03/06/2017.
 */
public class TcpChannel {
    private Socket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public TcpChannel(SocketAddress socketAddress, int timeout) throws IOException {
        System.out.println("Client :client trying to join.");
        mSocket = new Socket();
        mSocket.connect(socketAddress);
        setTimeOut(timeout);
        System.out.println("Client :client connected to server.");
        getStreams();
    }


    public TcpChannel(Socket socket, int timeout) {
        mSocket = socket;
        setTimeOut(timeout);
    }

    private void setTimeOut(int timeout) {
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
    public byte[] read() {
        byte[] a = new byte[4];
        byte[] b;
        byte[] c;
        try {
            mInputStream.read(a);
            ByteBuffer byteBuffer = ByteBuffer.wrap(a);
            int size = byteBuffer.getInt();
            b = new byte[size - 4];
            mInputStream.read(b);
            c = join2Array(a, b);
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
        return c;
    }

    private byte[] join2Array(byte[] a, byte[] b) {
        return ArrayUtils.addAll(a, b);
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
