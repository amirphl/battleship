package ir.aut.main.logic.messages;

/**
 * Created by Yana on 03/06/2017.
 */
public class MessageTypes {
    /**
     * Version of communication protocol
     */
    public static final byte PROTOCOL_VERSION = 1;
    /**
     * Code of request login message
     */
    public static final byte REQUEST_LOGIN = 1;

    public static final byte READINESS = 2;

    public static final byte LOCATION = 3;

    public static final byte ACCEPT = 4;

    public static final byte REQUEST_NAME = 5;

    public static final byte TERMINATE = 6;

    public static final byte CONVERSATION = 7;

    public static final byte REJECT = 8;

    public static final byte IP = 9;

    public static final byte REQUEST_LEAVE = 10;
}
