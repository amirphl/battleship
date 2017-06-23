package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 24/06/2017.
 */
public class IPMessage extends BaseMessage {
    private String mUsername;
    private String ip;

    public IPMessage(String username, String ip) {
        mUsername = username;
        this.ip = ip;
        serialize();
    }

    public IPMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int ipLength = ip.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + usernameLength + 4 + ipLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.IP);
        byteBuffer.putInt(usernameLength);
        byteBuffer.put(mUsername.getBytes());
        byteBuffer.putInt(ipLength);
        byteBuffer.put(ip.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.get();
        int usernameLength = byteBuffer.getInt();
        byte[] usernameBytes = new byte[usernameLength];
        byteBuffer.get(usernameBytes);
        int ipLength = byteBuffer.getInt();
        byte[] ipBytes = new byte[ipLength];
        byteBuffer.get(ipBytes);
        mUsername = new String(usernameBytes);
        ip = new String(ipBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.IP;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getIp() {
        return ip;
    }
}
