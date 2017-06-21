package ir.aut.test.logic;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 22/06/2017.
 */
public class AcceptMessage extends BaseMessage {

    public AcceptMessage() {
        serialize();
    }

    public AcceptMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ACCEPT);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ACCEPT;
    }
}
