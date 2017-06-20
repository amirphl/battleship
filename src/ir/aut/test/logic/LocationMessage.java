package ir.aut.test.logic;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 14/06/2017.
 */
public class LocationMessage extends BaseMessage {
    private byte mI;
    private byte mJ;
    private byte mCondition;

    public LocationMessage(byte i, byte j, byte condition) {
        mI = i;
        mJ = j;
        mCondition = condition;
        serialize();
    }

    public LocationMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 1 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.LOCATION);
        byteBuffer.put(mI);
        byteBuffer.put(mJ);
        byteBuffer.put(mCondition);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        mI = byteBuffer.get();
        mJ = byteBuffer.get();
        mCondition = byteBuffer.get();
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.LOCATION;
    }

    public byte getmI() {
        return mI;
    }

    public byte getmJ() {
        return mJ;
    }

    public byte getmCondition() {
        return mCondition;
    }

}
