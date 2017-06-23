package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 24/06/2017.
 */
public class RejectMessage extends BaseMessage {

    public RejectMessage() {
        serialize();
    }

    public RejectMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.REJECT);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.REJECT;
    }

}
