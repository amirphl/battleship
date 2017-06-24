package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 24/06/2017.
 */
public class RequestLeaveMessage extends BaseMessage {

    private String ip;

    public RequestLeaveMessage(String ip) {
        this.ip = ip;
        serialize();
    }

    public RequestLeaveMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int ipLength = ip.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + ipLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.REQUEST_LEAVE);
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
        int ipLength = byteBuffer.getInt();
        byte[] ipBytes = new byte[ipLength];
        byteBuffer.get(ipBytes);
        ip = new String(ipBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.REQUEST_LEAVE;
    }

    public String getIp() {
        return ip;
    }
}
