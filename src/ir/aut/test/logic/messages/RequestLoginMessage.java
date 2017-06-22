package ir.aut.test.logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Yana on 03/06/2017.
 */
public class RequestLoginMessage extends BaseMessage {
    private String mUsername;
    private String mPassword;

    public RequestLoginMessage(String username, String password) {
        mUsername = username;
        mPassword = password;
        serialize();
    }

    public RequestLoginMessage(byte[] serialized) {
        mSerialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int usernameLength = mUsername.getBytes().length;
        int passwordLength = mPassword.getBytes().length;
        int messageLength = 4 + 1 + 1 + 4 + usernameLength + 4 + passwordLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.REQUEST_LOGIN);
        byteBuffer.putInt(usernameLength);
        byteBuffer.put(mUsername.getBytes());
        byteBuffer.putInt(passwordLength);
        byteBuffer.put(mPassword.getBytes());
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
        int passwordLength = byteBuffer.getInt();
        byte[] passwordBytes = new byte[passwordLength];
        byteBuffer.get(passwordBytes);
        mPassword = new String(passwordBytes);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.REQUEST_LOGIN;
    }
    public String getUsername() {
        return mUsername;
    }
    public String getPassword() {
        return mPassword;
    }
}
