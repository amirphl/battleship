package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 22/06/2017.
 */
public class TerminateMessage extends BaseMessage {

    public TerminateMessage() {
        serialize();
    }

    public TerminateMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.TERMINATE);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.TERMINATE;
    }
}
