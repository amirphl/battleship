package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 22/06/2017.
 */
public class ConversationMessage extends BaseMessage {
    private String text;

    public ConversationMessage(String text) {
        this.text = text;
        serialize();
    }

    public ConversationMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int textLength = text.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + textLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.CONVERSATION);
        byteBuffer.putInt(textLength);
        byteBuffer.put(text.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.get();
        int textLength = byteBuffer.getInt();
        byte[] textBytes = new byte[textLength];
        byteBuffer.get(textBytes);
        text = new String(textBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.CONVERSATION;
    }

    public String getText() {
        return text;
    }
}
