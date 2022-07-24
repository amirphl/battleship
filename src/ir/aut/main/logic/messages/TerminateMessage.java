package ir.aut.main.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 22/06/2017.
 */
public class TerminateMessage extends BaseMessage {

    private int i;

    public TerminateMessage(int i) {
        this.i = i;
        serialize();
    }

    public TerminateMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.TERMINATE);
        byteBuffer.put((byte) i);
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int messageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        i = byteBuffer.get();
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.TERMINATE;
    }

    public byte getI(){
        return (byte) i;
    }
}
