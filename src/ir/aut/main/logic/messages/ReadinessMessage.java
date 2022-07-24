package ir.aut.main.logic.messages;

import ir.aut.main.logic.messages.BaseMessage;
import ir.aut.main.logic.messages.MessageTypes;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 06/06/2017.
 */
public class ReadinessMessage extends BaseMessage {

    private boolean readinessCondition;

    public ReadinessMessage(boolean readinessCondition) {
        this.readinessCondition = readinessCondition;
        serialize();
    }

    public ReadinessMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.READINESS);
        if (readinessCondition == true)
            byteBuffer.put((byte) 1);
        else
            byteBuffer.put((byte) 0);
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
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.READINESS;
    }

    public boolean getReadinessCondition() {
        return readinessCondition;
    }
}
