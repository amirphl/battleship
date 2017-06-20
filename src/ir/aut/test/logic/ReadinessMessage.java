package ir.aut.test.logic;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 06/06/2017.
 */
public class ReadinessMessage extends BaseMessage {

    private boolean readinessCondition;
    private String mUsername;

    public ReadinessMessage(boolean readinessCondition, String mUsername) {
        this.readinessCondition = readinessCondition;
        this.mUsername = mUsername;
        serialize();
    }

    public ReadinessMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int messageLength = 4 + 1 + 1 + 1 + 4 + usernameLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.READINESS);
        if (readinessCondition == true)
            byteBuffer.put((byte) 1);
        else
            byteBuffer.put((byte) 0);
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
        if (byteBuffer.get() == 1)
            readinessCondition = true;
        else
            readinessCondition = false;
        int usernameLength = byteBuffer.getInt();
        byte[] usernameBytes = new byte[usernameLength];
        byteBuffer.get(usernameBytes);
        mUsername = new String(usernameBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.READINESS;
    }

    public boolean getReadinessCondition() {
        return readinessCondition;
    }

    public String getmUsername() {
        return mUsername;
    }
}
