package ir.aut.test.logic.messages;

import ir.aut.test.logic.messages.BaseMessage;
import ir.aut.test.logic.messages.MessageTypes;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 21/06/2017.
 */
public class NameMessage extends BaseMessage {
    private String mUsername;

    public NameMessage(String username) {
        mUsername = username;
        serialize();
    }

    public NameMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + usernameLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.REQUEST_NAME);
        byteBuffer.putInt(usernameLength);
        byteBuffer.put(mUsername.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        int usernameLength = byteBuffer.getInt();
        byte[] usernameBytes = new byte[usernameLength];
        byteBuffer.get(usernameBytes);
        mUsername = new String(usernameBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.REQUEST_NAME;
    }

    public String getUsername() {
        return mUsername;
    }
}
